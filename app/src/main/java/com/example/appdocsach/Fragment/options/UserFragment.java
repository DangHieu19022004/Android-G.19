package com.example.appdocsach.Fragment.options;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdocsach.Activity.BookDetailActivity;
import com.example.appdocsach.Activity.Setting;
import com.example.appdocsach.Adapter.BooksAdapterVertical;
import com.example.appdocsach.Adapter.RecentlyReadAdapter;
import com.example.appdocsach.Adapter.SearchBookAdapter;
import com.example.appdocsach.R;
import com.example.appdocsach.model.BooksModel;
import com.example.appdocsach.model.User;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UserFragment extends Fragment {
    private RecyclerView rcvReadBooks;
    private RecentlyReadAdapter booksAdapter;
    private List<BooksModel> booksModelList;
    GoogleSignInOptions gso;

    private static final String ARG_USERNAME = "USERNAME";
    private static final String ARG_EMAIL = "EMAIL";

    private String username;
    private String email;
    TextView ctact;

    public UserFragment() {
        // Required empty public constructor
    }

    public static UserFragment newInstance(String username, String email) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, username);
        args.putString(ARG_EMAIL, email);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            username = getArguments().getString(ARG_USERNAME);
            email = getArguments().getString(ARG_EMAIL);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView usernameTextView = view.findViewById(R.id.username);
        TextView emailTextView = view.findViewById(R.id.email);
        ImageView setting = view.findViewById(R.id.setting);
        rcvReadBooks = view.findViewById(R.id.rcvReadBooks);

        usernameTextView.setText(username);
        emailTextView.setText(email);

        // Initialize RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        rcvReadBooks.setLayoutManager(linearLayoutManager);
        booksModelList = new ArrayList<>();
        booksAdapter = new RecentlyReadAdapter(getContext(), booksModelList, new RecentlyReadAdapter.IClickListener() {
            @Override
            public void onClickReadItemBook(BooksModel books) {
                // Handle book item click
                Intent intent = new Intent(getActivity(), BookDetailActivity.class);
                intent.putExtra("book_data", books);
                startActivity(intent);
            }
        });
        rcvReadBooks.setAdapter(booksAdapter);

        //Graph API facebook
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null && !accessToken.isExpired()) {
            GraphRequest request = GraphRequest.newMeRequest(
                    accessToken,
                    (object, response) -> {
                        // Application code
                        try {
                            assert object != null;
                            String name = object.getString("name");
                            usernameTextView.setText(name);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,link");
            request.setParameters(parameters);
            request.executeAsync();
        }

        //Setting
        setting.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), Setting.class));
            if (getActivity() != null) {
                getActivity().finish();
            }
        });
        //contact
        ctact = view.findViewById(R.id.contact);
        ctact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace current fragment with ContactFragment
                ContactFragment contactFragment = new ContactFragment();
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, contactFragment);
                transaction.addToBackStack(null); // Optional: Add to back stack
                transaction.commit();
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            // Load user data from Firebase
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users/" + userId);

            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User userData = snapshot.getValue(User.class);
                    if (userData != null) {
                        usernameTextView.setText(userData.getName());
                        emailTextView.setText(userData.getEmail());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getActivity(), "Không tải được dữ liệu người dùng", Toast.LENGTH_SHORT).show();
                }
            });

            // Load recently read books data
            DatabaseReference userBooksRef = FirebaseDatabase.getInstance().getReference("Users/" + userId + "/readBooks");

            userBooksRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    booksModelList.clear();
                    List<Pair<String, Long>> bookTimestamps = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String bookId = dataSnapshot.getKey();
                        Long timestamp = dataSnapshot.getValue(Long.class);
                        if (bookId != null && timestamp != null) {
                            bookTimestamps.add(new Pair<>(bookId, timestamp));
                        }
                    }

                    // Sắp xếp danh sách sách theo thời gian cuối cùng đọc giảm dần
                    Collections.sort(bookTimestamps, (o1, o2) -> Long.compare(o2.second, o1.second));
                    // Load book details and update adapter
                    for (Pair<String, Long> pair : bookTimestamps) {
                        getBookById(pair.first, book -> {
                            if (book != null) {
                                booksModelList.add(book);
                                booksAdapter.setData(new ArrayList<>(booksModelList));
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getActivity(), "Không thể tải sách", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void getBookById(String bookId, OnBookLoadedListener listener) {
        DatabaseReference bookRef = FirebaseDatabase.getInstance().getReference("books/" + bookId);
        bookRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                BooksModel book = snapshot.getValue(BooksModel.class);
                listener.onBookLoaded(book);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Không thể tải sách", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private interface OnBookLoadedListener {
        void onBookLoaded(BooksModel book);
    }
}
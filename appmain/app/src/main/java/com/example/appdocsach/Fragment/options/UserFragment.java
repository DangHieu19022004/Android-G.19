package com.example.appdocsach.Fragment.options;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdocsach.Activity.Setting;
import com.example.appdocsach.Adapter.BooksAdapterVertical;
import com.example.appdocsach.Adapter.RecentlyReadAdapter;
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
        booksAdapter = new RecentlyReadAdapter(getActivity(), new ArrayList<>());
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

        // Load user data from Firebase
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
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
                    Toast.makeText(getActivity(), "Failed to load user data", Toast.LENGTH_SHORT).show();
                }
            });

            // Load recently read books data
            DatabaseReference userBooksRef = FirebaseDatabase.getInstance().getReference("Users/" + userId + "/readBooks");

            // Kiểm tra và tạo đường dẫn "readBooks" nếu chưa tồn tại
            userBooksRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.exists()) {
                        // Create the path if it doesn't exist
                        userBooksRef.setValue("");
                    }

                    // Now load the recently read books data
                    userBooksRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            booksModelList.clear();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                BooksModel book = dataSnapshot.getValue(BooksModel.class);
                                if (book != null) {
                                    booksModelList.add(book);
                                }
                            }

                            // Sắp xếp danh sách sách theo timestamp giảm dần
                            Collections.sort(booksModelList, new Comparator<BooksModel>() {
                                @Override
                                public int compare(BooksModel o1, BooksModel o2) {
                                    return Long.compare(o2.getTimestamp(), o1.getTimestamp());
                                }
                            });

                            booksAdapter.setData(new ArrayList<>(booksModelList));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getActivity(), "Failed to load books", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle possible errors.
                }
            });
        }
    }
}
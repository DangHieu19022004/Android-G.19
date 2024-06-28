package com.example.appdocsach.Fragment.options;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appdocsach.Activity.BookDetailActivity;
import com.example.appdocsach.Activity.EditPostBookActivity;
import com.example.appdocsach.Activity.PostBookActivity;
import com.example.appdocsach.Adapter.BooksAdapterPost;
import com.example.appdocsach.R;
import com.example.appdocsach.model.BooksModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;


public class ManageBookFragment extends Fragment {

    private RecyclerView rcvBookManagePost;

    int posisionid;
    private ImageView createbooknewbtnmanage;
    List<BooksModel> mListBookManage;
    public static BooksAdapterPost booksAdapterPost;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    String author = FirebaseAuth.getInstance().getCurrentUser().getUid();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ManageBookFragment() {
        // Required empty public constructor
    }


    public static ManageBookFragment newInstance(String param1, String param2) {
        ManageBookFragment fragment = new ManageBookFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manage_book, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapping(view);

        //declare list book
        mListBookManage = new ArrayList<>();


        //show to screen
        booksAdapterPost = new BooksAdapterPost(mListBookManage, new BooksAdapterPost.IClickListener() {
            @Override
            public void onClickReadItemBook(BooksModel books) {
                showDetailBook(books);
            }

            @Override
            public void onClickDeleteItemBook(BooksModel books, int position) {
                showAlertDialogDelete(books, position);
            }

            @Override
            public void onClickEditItemBook(BooksModel books, int position) {
                editbook(books, position);
            }
        });

        rcvBookManagePost.setAdapter(booksAdapterPost);
        rcvBookManagePost.setLayoutManager(new GridLayoutManager(getContext(), 1));

        //get book from user
        getBookOfAuthor();

        //click to create new book
        createbooknewbtnmanage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), PostBookActivity.class);
                startActivity(it);
            }
        });

    }

    private void editbook(BooksModel books, int position) {
        Intent intent = new Intent(getActivity(), EditPostBookActivity.class);

        intent.putExtra("book_data", books);

        startActivity(intent);
    }

    private void showAlertDialogDelete(BooksModel books, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Thông báo");
        builder.setMessage("Xóa khỏi thiết bị?");
        builder.setPositiveButton("OK", (dialog, which) -> {
            posisionid = position;
            DeleteBookFromFirebase(books);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void DeleteBookFromFirebase(BooksModel books) {
        DatabaseReference myRef = database.getReference("books/" + books.getId());

        myRef.removeValue();
    }

    private void getBookOfAuthor() {
        DatabaseReference myRef = database.getReference("books");
        Query query = myRef.orderByChild("author").equalTo(author);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                BooksModel booksModel = snapshot.getValue(BooksModel.class);
                if (booksModel != null) {
                    mListBookManage.add(booksModel);
                    booksAdapterPost.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                BooksModel booksModel = snapshot.getValue(BooksModel.class);
                if(booksModel == null || mListBookManage == null || mListBookManage.isEmpty()){return;}

                for (int i = 0; i < mListBookManage.size(); i++) {
                    if (booksModel.getId().equals(mListBookManage.get(i).getId())) {
                        mListBookManage.set(i, booksModel);
                        break;
                    }
                }
                booksAdapterPost.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                BooksModel booksModel = snapshot.getValue(BooksModel.class);
                if(booksModel == null || mListBookManage == null || mListBookManage.isEmpty()){return;}

                for (int i = 0; i < mListBookManage.size(); i++) {
                    if (booksModel.getId().equals(mListBookManage.get(i).getId())) {
                        mListBookManage.remove(i);
                        break;
                    }
                }
                booksAdapterPost.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // Xử lý khi có sự di chuyển dữ liệu
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Hiển thị thất bại" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDetailBook(BooksModel books) {
        Intent it = new Intent(getActivity(), BookDetailActivity.class);
        it.putExtra("book_data", books);

        startActivity(it);
    }
    private void mapping(View view) {
        createbooknewbtnmanage = view.findViewById(R.id.createbooknewbtnmanage);
        rcvBookManagePost = view.findViewById(R.id.rcvBookManagePost);
    }
}
package com.example.appdocsach.Fragment.options;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import com.example.appdocsach.Activity.BookDetailActivity;
import com.example.appdocsach.Adapter.BooksAdapterManage;
import com.example.appdocsach.DatabaseHelper;
import com.example.appdocsach.R;
import com.example.appdocsach.model.BooksModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SavedBookFragment extends Fragment {
    int posisionid;
    private DatabaseHelper databaseHelper;
    private RecyclerView rclvManage;
    public static BooksAdapterManage booksAdapterManage;

    List<BooksModel> mListBookManage;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public SavedBookFragment() {
        // Required empty public constructor
    }


    public static SavedBookFragment newInstance(String param1, String param2) {
        SavedBookFragment fragment = new SavedBookFragment();
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
        return inflater.inflate(R.layout.fragment_saved_book, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //mapping
        mapping(view);

        //declare list book
        mListBookManage = new ArrayList<>();


        //show to screen
        booksAdapterManage = new BooksAdapterManage(mListBookManage, new BooksAdapterManage.IClickListener() {
            @Override
            public void onClickReadItemBook(BooksModel books) {
                showDetailBook(books);
            }

            @Override
            public void onClickDeleteItemBook(BooksModel books, int position) {
                showAlertDialogDelete(books, position);
            }
        });

        rclvManage.setAdapter(booksAdapterManage);
        rclvManage.setLayoutManager(new GridLayoutManager(getContext(), 1));

    }

    @Override
    public void onResume() {
        super.onResume();
        updateBookList();
    }

    private void updateBookList() {
        //get book form sqlite
        mListBookManage.clear();
        mListBookManage.addAll(databaseHelper.getAllBooks());
        booksAdapterManage.notifyDataSetChanged();
    }


    private void showAlertDialogDelete(BooksModel books, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Thông báo");
        builder.setMessage("Xóa khỏi thiết bị?");
        builder.setPositiveButton("OK", (dialog, which) -> {
            posisionid = position;
            DeleteBookFromSQLite(books, position);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void DeleteBookFromSQLite(BooksModel books, int position) {
        if(databaseHelper.deleteBook(books.getId()) == 0){
            Toast.makeText(getContext(), "Xóa thất bại, vui lòng thử lại sau", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();

            mListBookManage.remove(position);

            booksAdapterManage.notifyItemRemoved(position);
            booksAdapterManage.notifyItemRangeChanged(position, mListBookManage.size());
        }
    }


    private void showDetailBook(BooksModel books) {
        Intent it = new Intent(getActivity(), BookDetailActivity.class);
        it.putExtra("book_data", books);

        startActivity(it);
    }
    private void mapping(View view) {
        rclvManage = view.findViewById(R.id.rcvBookSaved);
        databaseHelper = new DatabaseHelper(getContext());
    }
}
package com.example.appdocsach.Fragment.options;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appdocsach.Adapter.BooksAdapterManage;
import com.example.appdocsach.DatabaseHelper;
import com.example.appdocsach.R;
import com.example.appdocsach.model.BooksModel;

import java.util.ArrayList;
import java.util.List;

public class SavedBookFragment extends Fragment {

    private DatabaseHelper databaseHelper;
    private RecyclerView rclvManage;
    private BooksAdapterManage booksAdapterManage;

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

        //get book form sqlite
        mListBookManage = databaseHelper.getAllBooks();

        //show to screen
        booksAdapterManage = new BooksAdapterManage(mListBookManage, new BooksAdapterManage.IClickListener() {
            @Override
            public void onClickReadItemBook(BooksModel books) {

            }

            @Override
            public void onClickDeleteItemBook(BooksModel books) {

            }
        });

        rclvManage.setAdapter(booksAdapterManage);
        rclvManage.setLayoutManager(new GridLayoutManager(getContext(), 1));

    }

    private void mapping(View view) {
        rclvManage = view.findViewById(R.id.rcvBookSaved);
        databaseHelper = new DatabaseHelper(getContext());
    }
}
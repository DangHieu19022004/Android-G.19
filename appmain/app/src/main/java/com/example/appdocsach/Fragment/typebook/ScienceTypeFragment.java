package com.example.appdocsach.Fragment.typebook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdocsach.Adapter.BooksAdapter;
import com.example.appdocsach.model.BooksModel;
import com.example.appdocsach.model.Database;
import com.example.appdocsach.R;

import java.util.List;

public class ScienceTypeFragment extends Fragment {
    private RecyclerView recyclerView;
    private BooksAdapter adapter;
    private List<BooksModel> scienceBookList;
    private Database database; // Database instance to load books

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize Database instance
        database = new Database();
        database.loadBooksFromJson(getResources().openRawResource(R.raw.database));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_science_type, container, false);
        recyclerView = rootView.findViewById(R.id.recyclerView);

        // Initialize and populate scienceBookList
        initScienceBookList();

        // Initialize and set adapter for RecyclerView
        adapter = new BooksAdapter(getContext(), scienceBookList, new BooksAdapter.IClickListener() {
            @Override
            public void onClickReadItemBook(BooksModel books) {
                // Handle click event
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    private void initScienceBookList() {
        scienceBookList = database.getBooksByCategory(1); // ID của thể loại "Khoa học" là 1
    }
}

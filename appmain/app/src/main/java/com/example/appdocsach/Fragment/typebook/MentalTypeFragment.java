package com.example.appdocsach.Fragment.typebook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdocsach.Adapter.BookAdapter;
import com.example.appdocsach.Book;
import com.example.appdocsach.Database;
import com.example.appdocsach.R;

import java.util.List;

public class MentalTypeFragment extends Fragment {
    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private List<Book> mentalBookList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_mental_type, container, false);
        recyclerView = rootView.findViewById(R.id.recyclerView);

        initMentalBookList();

        adapter = new BookAdapter(getContext(), mentalBookList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return rootView;
    }
    private void initMentalBookList() {
        Database database = new Database();
        database.loadBooksFromJson(getResources().openRawResource(R.raw.database));

        mentalBookList = database.getBooksByCategory(2); // ID của thể loại "Tâm lý" là 2
        List<Book> allBooks = database.getBookList();
        for (Book book : allBooks) {
            if (book.getCategoryId() == 2) {
                mentalBookList.add(book);
            }
        }
    }
}
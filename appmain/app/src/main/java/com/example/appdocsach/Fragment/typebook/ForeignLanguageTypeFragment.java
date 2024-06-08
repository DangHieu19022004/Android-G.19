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

public class ForeignLanguageTypeFragment extends Fragment {
    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private List<Book> languageBookList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_language_type, container, false);
        recyclerView = rootView.findViewById(R.id.recyclerView);

        initCultureBookList();

        adapter = new BookAdapter(getContext(), languageBookList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return rootView;
    }
    private void initCultureBookList() {
        Database database = new Database();
        database.loadBooksFromJson(getResources().openRawResource(R.raw.database));
        // Lọc ra các cuốn sách thuộc thể loại "Văn hóa"
        languageBookList = database.getBooksByCategory(4);
        List<Book> allBooks = database.getBookList();
        for (Book book : allBooks) {
            if (book.getCategoryId() == 4) {
                languageBookList.add(book);
            }
        }
    }
}
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

import java.util.ArrayList;
import java.util.List;

public class ScienceTypeFragment extends Fragment {
    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private List<Book> scienceBookList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_science_type, container, false);
        recyclerView = rootView.findViewById(R.id.recyclerView);

        // Khởi tạo danh sách sách khoa học
        initScienceBookList();

        // Khởi tạo và thiết lập adapter cho RecyclerView
        adapter = new BookAdapter(getContext(), scienceBookList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return rootView;
    }
    private void initScienceBookList() {
        // Lấy danh sách sách từ cơ sở dữ liệu
        Database database = new Database();
        database.loadBooksFromJson(getResources().openRawResource(R.raw.database));

        // Lọc ra các cuốn sách thuộc thể loại "Khoa học"
        scienceBookList = database.getBooksByCategory(1);
        List<Book> allBooks = database.getBookList();
        for (Book book : allBooks) {
            if (book.getCategoryId() == 1) {
                scienceBookList.add(book);
            }
        }
    }
}
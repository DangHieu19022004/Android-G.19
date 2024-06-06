package com.example.appdocsach.Fragment.options;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.appdocsach.Adapter.viewpagerTypeBookAdapter;
import com.example.appdocsach.Book;
import com.example.appdocsach.Database;
import com.example.appdocsach.MainActivity;
import com.example.appdocsach.R;
import com.example.appdocsach.widget.CustomViewPager;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private TabLayout tabLayout;
    private CustomViewPager viewPager;
    private View mview;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private Database database;

    // Danh sách sách từ file database.json
    private List<Book> bookList;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        mview = inflater.inflate(R.layout.fragment_home, container, false);

        tabLayout = mview.findViewById(R.id.tablayoutMain);
        viewPager = mview.findViewById(R.id.viewpageType);
        EditText searchInput = mview.findViewById(R.id.search_input);
        ImageView searchIcon = mview.findViewById(R.id.search_icon);

        database = new Database();
        loadBooksFromJson();

        viewpagerTypeBookAdapter optionadapter = new viewpagerTypeBookAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(optionadapter);
        viewPager.setPagingEnabled(false);

        tabLayout.setupWithViewPager(viewPager);

        //Hiện edittext khi nhấn vào icon
        searchIcon.setOnClickListener(v -> {
            if (searchInput.getVisibility() == View.GONE) {
                searchInput.setVisibility(View.VISIBLE);
            } else {
                searchInput.setVisibility(View.GONE);
            }
        });

        //Xử lý tìm kiếm
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.hideBottomNavigationView();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // Nếu không có văn bản nào, hiển thị lại BottomNavigationView
                if (s.length() == 0) {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    if (mainActivity != null) {
                        mainActivity.showBottomNavigationView();
                    }
                }
                List<Book> filteredList = database.searchBooksByTitle(s.toString());
                if (filteredList.isEmpty()) {
                    Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
                } else {
                    // Hiển thị danh sách kết quả
                    for (Book book : filteredList){
                        Toast.makeText(getContext(), book.getTitle(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return mview;
    }

    private void loadBooksFromJson() {
        //Cannot resolve symbol 'Database'
        InputStream inputStream = getResources().openRawResource(R.raw.database);
        Gson gson = new Gson();
        InputStreamReader reader = new InputStreamReader(inputStream);

        // Đọc dữ liệu từ JSON vào đối tượng Database
        database = gson.fromJson(reader, Database.class);
    }
}
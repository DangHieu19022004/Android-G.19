package com.example.appdocsach.Fragment.options;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdocsach.Adapter.BooksAdapterVertical;
import com.example.appdocsach.Adapter.viewpagerTypeBookAdapter;
import com.example.appdocsach.R;
import com.example.appdocsach.model.BooksModel;
import com.example.appdocsach.model.Database;
import com.example.appdocsach.widget.CustomViewPager;
import com.google.android.material.internal.ContextUtils;
import com.google.android.material.tabs.TabLayout;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class HomeFragment extends Fragment {
    private TabLayout tabLayout;
    private CustomViewPager viewPager;
    private List<BooksModel> booksList;
    private View mview;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private Database database;
    private EditText searchInput;
    private ImageView searchIcon;
    private RecyclerView searchResultsRecyclerView;
    private BooksAdapterVertical booksAdapter;

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
        database = new Database();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mview = inflater.inflate(R.layout.fragment_home, container, false);

        tabLayout = mview.findViewById(R.id.tablayoutMain);
        viewPager = mview.findViewById(R.id.viewpageType);
        searchInput = mview.findViewById(R.id.search_input);
        searchIcon = mview.findViewById(R.id.search_icon);
        searchResultsRecyclerView = mview.findViewById(R.id.search_results_recycler_view);

        viewpagerTypeBookAdapter optionadapter = new viewpagerTypeBookAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(optionadapter);
        viewPager.setPagingEnabled(false);

        tabLayout.setupWithViewPager(viewPager);

        //Cài đặt cho RecyclerView để tìm kiếm
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        searchResultsRecyclerView.setLayoutManager(layoutManager);
        booksList = new ArrayList<>();
        booksAdapter = new BooksAdapterVertical(booksList, new BooksAdapterVertical.IClickListener() {
            @Override
            public void onClickReadItemBook(BooksModel books) {
                // Handle item click if needed
            }
        });
        searchResultsRecyclerView.setAdapter(booksAdapter);

        //Hiện edittext khi nhấn vào icon
        searchIcon.setOnClickListener(v -> {
            if (searchInput.getVisibility() == View.GONE) {
                searchInput.setVisibility(View.VISIBLE);
            } else {
                searchInput.setVisibility(View.GONE);
                searchInput.setText("");
                displaySearchResults(new ArrayList<>());
            }
        });

        searchInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch(searchInput.getText().toString().trim());
                return true;
            }
            return false;
        });

        //Xử lý tìm kiếm khi gõ từ khoá
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //Xử lý tìm kiếm khi gõ từ khoá
                performSearch(s.toString().trim());
            }
        });
        return mview;
    }
    private void performSearch(String searchText) {
        if (searchText.isEmpty()) {
            clearSearchResults();
            return;
        }
        database.searchBooksByTitle(searchText, new Database.OnSearchCompleteListener() {
            @Override
            public void onSearchComplete(List<BooksModel> books) {
                displaySearchResults(books);
            }
            @Override
            public void onSearchError(String error) {
                Toast.makeText(getContext(), "Search error: " + error, Toast.LENGTH_SHORT).show();
                clearSearchResults();
            }
        });
    }

    // Hiển thị kết quả
    private void displaySearchResults(List<BooksModel> books) {
        booksList.clear();
        if (books == null) {
            booksList.addAll(books);
        }
        booksAdapter.notifyDataSetChanged();

        // Hiển thị RecyclerView and ẩn ViewPager
        searchResultsRecyclerView.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.GONE);
        tabLayout.setVisibility(View.GONE);
    }

    private void clearSearchResults() {
        booksList.clear();
        booksAdapter.notifyDataSetChanged();

        // Hiển thị RecyclerView and ẩn ViewPager
        viewPager.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.VISIBLE);
        searchResultsRecyclerView.setVisibility(View.GONE);
    }
}
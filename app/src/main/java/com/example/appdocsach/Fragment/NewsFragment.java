package com.example.appdocsach.Fragment;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.appdocsach.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class NewsFragment extends Fragment {
    DrawerLayout drawerLayout;
    LinearProgressIndicator progressIndicator;
    private RecyclerView rcvArticle;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    EditText search_edit;
    TabItem item0,item1,item2,item3,item4,item5,item6;
    TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        item0 = view.findViewById(R.id.item0);
        item1 = view.findViewById(R.id.item1);
        item2 = view.findViewById(R.id.item2);
        item3 = view.findViewById(R.id.item3);
        item4 = view.findViewById(R.id.item4);
        tabLayout = view.findViewById(R.id.tabLayout);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Xử lý khi một tab được chọn
                int position = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Xử lý khi một tab không còn được chọn
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Xử lý khi một tab đã được chọn lại
            }
        });

        rcvArticle = view.findViewById(R.id.recycleview_items);
        progressIndicator = view.findViewById(R.id.progress_bar);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
//        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
//        dialog.show();


        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        drawerLayout = view.findViewById(R.id.drawerlayout);
        NavigationView navigationView = view.findViewById(R.id.navigation_view);

    }
    void changeInProgress(boolean show){
        if(show){
            progressIndicator.setVisibility(View.VISIBLE);
        }else{
            progressIndicator.setVisibility(View.INVISIBLE);
        }
    }
}
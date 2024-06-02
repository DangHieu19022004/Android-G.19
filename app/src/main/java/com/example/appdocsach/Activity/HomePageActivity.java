package com.example.appdocsach.Activity;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.appdocsach.R;
import com.example.appdocsach.Adapter.viewpagerTypeBookAdapter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.tabs.TabLayout;

public class HomePageActivity extends AppCompatActivity {
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    TabLayout tabLayout;
    ViewPager viewPagerType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);



        Mapping();
        setupviewpagertablayout();

    }

    private void setupviewpagertablayout() {
        viewpagerTypeBookAdapter viewpagerAdapter = new viewpagerTypeBookAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerType.setAdapter(viewpagerAdapter);

        tabLayout.setupWithViewPager(viewPagerType);
    }

    private void Mapping() {
        viewPagerType = findViewById(R.id.viewpageType);
        tabLayout = findViewById(R.id.tablayout);
    }



}
package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.login.viewpageradapter.viewpagerTypeBookAdapter;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;

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
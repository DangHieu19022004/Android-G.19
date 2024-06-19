package com.example.appdocsach;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.appdocsach.Adapter.viewpagerOptions;
import com.example.appdocsach.BroadcastReceiver.Internet;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPagerOption;
    BottomNavigationView bottomNavigationView;
    private Internet internetBroadcastReceiver;
    private boolean isReceiverRegistered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        internetBroadcastReceiver = new Internet();
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Mapping();
        setupviewpagernavbottom();

        bottomNavigationView.setOnItemSelectedListener(menuItem -> {
            int itemId = menuItem.getItemId();
            if (itemId == R.id.home) {
                viewPagerOption.setCurrentItem(0);
            } else if (itemId == R.id.save) {
                viewPagerOption.setCurrentItem(1);
            } else if (itemId == R.id.pen) {
                viewPagerOption.setCurrentItem(2);
            } else if (itemId == R.id.user) {
                viewPagerOption.setCurrentItem(3);
            } else {
                return false;
            }

            return true;
        });

    }

    private void Mapping() {
        viewPagerOption = findViewById(R.id.viewpageOption);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
    }

    private void setupviewpagernavbottom() {
        viewpagerOptions viewpagerAdapter = new viewpagerOptions(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerOption.setAdapter(viewpagerAdapter);

        viewPagerOption.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    bottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);
                } else if (position == 1) {
                    bottomNavigationView.getMenu().findItem(R.id.save).setChecked(true);
                } else if (position == 2) {
                    bottomNavigationView.getMenu().findItem(R.id.pen).setChecked(true);
                } else if (position == 3) {
                    bottomNavigationView.getMenu().findItem(R.id.user).setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    //Ẩn khi tìm kiếm
    public void hideBottomNavigationView() {
        bottomNavigationView.setVisibility(View.GONE);
    }
    //Hiện khi không tìm kiếm được
    public void showBottomNavigationView() {
        bottomNavigationView.setVisibility(View.VISIBLE);
    }

    protected void onResume() {
        super.onResume();

        // Đăng ký BroadcastReceiver khi Activity được hiển thị
        if (!isReceiverRegistered) {
            registerReceiver(internetBroadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            isReceiverRegistered = true;
        }
    }
    protected void onPause() {
        super.onPause();

        // Hủy đăng ký BroadcastReceiver khi Activity không còn hiển thị
        if (isReceiverRegistered) {
            unregisterReceiver(internetBroadcastReceiver);
            isReceiverRegistered = false;
        }
    }
}
package com.example.appdocsach;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.appdocsach.Adapter.viewpagerOptions;
import com.example.appdocsach.BroadcastReceiver.Internet;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPagerOption;
    BottomNavigationView bottomNavigationView;
    private Internet internetBroadcastReceiver;
    private boolean isReceiverRegistered = false;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo FirebaseAnalytics
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // Kích hoạt hiển thị edge-to-edge
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            // Thiết lập padding cho các cạnh theo hệ thống cửa sổ
            v.setPadding(insets.getSystemWindowInsetLeft(), insets.getSystemWindowInsetTop(),
                    insets.getSystemWindowInsetRight(), insets.getSystemWindowInsetBottom());
            return insets.consumeSystemWindowInsets();
        });

        // Ánh xạ các View từ XML
        Mapping();

        // Thiết lập ViewPager và BottomNavigationView
        setupViewPagerAndBottomNav();
    }

    // Phương thức ánh xạ các View từ XML
    private void Mapping() {
        viewPagerOption = findViewById(R.id.viewpageOption);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
    }

    // Phương thức thiết lập ViewPager và BottomNavigationView
    private void setupViewPagerAndBottomNav() {
        // Khởi tạo adapter cho ViewPager
        viewpagerOptions viewpagerAdapter = new viewpagerOptions(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerOption.setAdapter(viewpagerAdapter);

        // Bắt sự kiện khi ViewPager được chuyển đổi
        viewPagerOption.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                // Đánh dấu MenuItem tương ứng trên BottomNavigationView khi ViewPager được chuyển đổi
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        // Xử lý sự kiện khi chọn MenuItem trên BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                viewPagerOption.setCurrentItem(0);
            } else if (itemId == R.id.save) {
                viewPagerOption.setCurrentItem(1);
            } else if (itemId == R.id.pen) {
                viewPagerOption.setCurrentItem(2);
            } else if (itemId == R.id.user) {
                viewPagerOption.setCurrentItem(3);
            }
            return true;
        });
    }

    // Xử lý khi người dùng nhấn vào biểu tượng chatbot
    public void showChatDialog(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ChatFragment chatFragment = new ChatFragment();
        chatFragment.show(fragmentManager, "chat_fragment");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Đăng ký BroadcastReceiver khi Activity được hiển thị
        if (!isReceiverRegistered) {
            registerReceiver(internetBroadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            isReceiverRegistered = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Hủy đăng ký BroadcastReceiver khi Activity không còn hiển thị
        try {
            if (isReceiverRegistered) {
                unregisterReceiver(internetBroadcastReceiver);
                isReceiverRegistered = false;
            }
        } catch (Exception e){
        }
    }
}

//package com.example.appdocsach.Activity;
//
//import android.os.Bundle;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.FragmentStatePagerAdapter;
//import androidx.viewpager.widget.ViewPager;
//
//import com.example.appdocsach.Adapter.viewpagerOptions;
//import com.example.appdocsach.R;
//import com.example.appdocsach.Adapter.viewpagerTypeBookAdapter;
//import com.google.android.gms.auth.api.signin.GoogleSignInClient;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//import com.google.android.material.tabs.TabLayout;
//
//public class HomePageActivity extends AppCompatActivity {
//    GoogleSignInOptions gso;
//    GoogleSignInClient gsc;
//    TabLayout tabLayout;
//    ViewPager viewPagerType;
//    BottomNavigationView bottomNavigationView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//
//
//        Mapping();
////        setupviewpagertablayout();
////        setupviewpagernavbottom();
//
////        bottomNavigationView.setOnItemSelectedListener(menuItem -> {
////            int itemId = menuItem.getItemId();
////            if (itemId == R.id.home) {
////                viewPagerType.setCurrentItem(0);
////            } else if (itemId == R.id.save) {
////                viewPagerType.setCurrentItem(1);
////            } else if (itemId == R.id.pen) {
////                viewPagerType.setCurrentItem(2);
////            } else if (itemId == R.id.user) {
////                viewPagerType.setCurrentItem(3);
////            } else {
////                return false;
////            }
////
////            return true;
////        });
//    }
//
//    private void setupviewpagertablayout() {
//        viewpagerTypeBookAdapter viewpagerAdapter = new viewpagerTypeBookAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
//        viewPagerType.setAdapter(viewpagerAdapter);
//
//        tabLayout.setupWithViewPager(viewPagerType);
//    }
//
//    private void Mapping() {
//        viewPagerType = findViewById(R.id.viewpageType);
//        tabLayout = findViewById(R.id.tablayout);
//        bottomNavigationView = findViewById(R.id.bottomNavigationView);
//    }
//
//    private void setupviewpagernavbottom() {
//        viewpagerOptions viewpagerAdapter = new viewpagerOptions(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
//        viewPagerType.setAdapter(viewpagerAdapter);
//
//        viewPagerType.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                if (position == 0) {
//                    bottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);
//                } else if (position == 1) {
//                    bottomNavigationView.getMenu().findItem(R.id.save).setChecked(true);
//                } else if (position == 2) {
//                    bottomNavigationView.getMenu().findItem(R.id.pen).setChecked(true);
//                } else if (position == 3) {
//                    bottomNavigationView.getMenu().findItem(R.id.user).setChecked(true);
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//
//    }
//
//}

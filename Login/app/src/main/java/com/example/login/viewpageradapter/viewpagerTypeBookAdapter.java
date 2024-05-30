package com.example.login.viewpageradapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.login.fragment.typebook.AllTypeFragment;
import com.example.login.fragment.typebook.CultureTypeFragment;
import com.example.login.fragment.typebook.HistoryTypeFragment;
import com.example.login.fragment.typebook.MentalTypeFragment;
import com.example.login.fragment.typebook.NovelTypeFragment;
import com.example.login.fragment.typebook.ScienceTypeFragment;

public class viewpagerTypeBookAdapter extends FragmentStatePagerAdapter {



    public viewpagerTypeBookAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new AllTypeFragment();
            case 1:
                return new ScienceTypeFragment();
            case 2:
                return new MentalTypeFragment();
            case 3:
                return new NovelTypeFragment();
            case 4:
                return new CultureTypeFragment();
            case 5:
                return new HistoryTypeFragment();
            default:
                return new AllTypeFragment();
        }
    }

    @Override
    public int getCount() {
        return 6;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Tất cả";
                break;
            case 1:
                title = "Khoa học";
                break;
            case 2:
                title = "Tâm lý";
                break;
            case 3:
                title = "Tiểu thuyết";
                break;
            case 4:
                title = "Văn hóa";
                break;
            case 5:
                title = "Lịch sử";
                break;
        }
        return title;
    }
}

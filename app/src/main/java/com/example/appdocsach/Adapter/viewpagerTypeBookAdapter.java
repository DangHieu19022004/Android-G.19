package com.example.appdocsach.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.appdocsach.Fragment.typebook.AllTypeFragment;
import com.example.appdocsach.Fragment.typebook.ForeignLanguageTypeFragment;
import com.example.appdocsach.Fragment.typebook.CookTypeFragment;
import com.example.appdocsach.Fragment.typebook.MentalTypeFragment;
import com.example.appdocsach.Fragment.typebook.NovelTypeFragment;
import com.example.appdocsach.Fragment.typebook.ScienceTypeFragment;

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
                return new ForeignLanguageTypeFragment();
            case 5:
                return new CookTypeFragment();
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
                title = "Ngoại ngữ";
                break;
            case 5:
                title = "Nấu ăn";
                break;
        }
        return title;
    }
}
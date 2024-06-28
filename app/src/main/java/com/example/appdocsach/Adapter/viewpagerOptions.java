package com.example.appdocsach.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.appdocsach.Fragment.options.HomeFragment;
import com.example.appdocsach.Fragment.options.ManageBookFragment;
import com.example.appdocsach.Fragment.options.SavedBookFragment;
import com.example.appdocsach.Fragment.options.UserFragment;
import com.example.appdocsach.Fragment.typebook.AllTypeFragment;

public class viewpagerOptions extends FragmentStatePagerAdapter {

    public viewpagerOptions(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new SavedBookFragment();
            case 2:
                return new ManageBookFragment();
            case 3:
                return new UserFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

}
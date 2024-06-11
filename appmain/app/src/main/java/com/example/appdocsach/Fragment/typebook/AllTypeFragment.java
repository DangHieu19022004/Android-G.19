package com.example.appdocsach.Fragment.typebook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.appdocsach.Adapter.viewpagerSlide;
import com.example.appdocsach.R;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class AllTypeFragment extends Fragment {

    private ViewPager viewPagerSlide;
    private CircleIndicator circleIndicatorSlide;
    private viewpagerSlide viewpagerSlideAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_type, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Mapping view
        viewPagerSlide = view.findViewById(R.id.viewpagerSlide);
        circleIndicatorSlide = view.findViewById(R.id.circleindicatorSlide);

        List<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://firebasestorage.googleapis.com/v0/b/appdocsach-18d2f.appspot.com/o/Image%2FScreenshot%202024-06-11%20001954.png?alt=media&token=a3dd5fc6-1ea2-45a6-b8b0-5a85e9d453aa");
        mangquangcao.add("https://firebasestorage.googleapis.com/v0/b/appdocsach-18d2f.appspot.com/o/Image%2FScreenshot%202024-06-11%20002148.png?alt=media&token=d7223bba-23b8-4eab-ab28-76ed83798b6b");
        mangquangcao.add("https://firebasestorage.googleapis.com/v0/b/appdocsach-18d2f.appspot.com/o/Image%2FScreenshot%202024-06-11%20002342.png?alt=media&token=613fec23-ce1e-41fc-bc9c-b19690daec9d");
        mangquangcao.add("https://firebasestorage.googleapis.com/v0/b/appdocsach-18d2f.appspot.com/o/Image%2FScreenshot%202024-06-11%20002454.png?alt=media&token=d502cbdb-e53b-439e-800f-6dec2fbfd6b9");
        mangquangcao.add("https://firebasestorage.googleapis.com/v0/b/appdocsach-18d2f.appspot.com/o/Image%2FScreenshot%202024-06-11%20000640.png?alt=media&token=3a540cc7-e93e-4895-9973-d47b7ca75be5");

        viewpagerSlideAdapter = new viewpagerSlide(getContext(), mangquangcao);
        viewPagerSlide.setAdapter(viewpagerSlideAdapter);

        circleIndicatorSlide.setViewPager(viewPagerSlide);

        viewpagerSlideAdapter.registerDataSetObserver(circleIndicatorSlide.getDataSetObserver());

    }


}
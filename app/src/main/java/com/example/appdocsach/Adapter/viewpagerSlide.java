package com.example.appdocsach.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.appdocsach.R;

import java.util.List;

public class viewpagerSlide extends PagerAdapter {
    private Context mContext;
    private List<String> mListPhoto;

    public viewpagerSlide(Context mContext, List<String> mListPhoto) {
        this.mContext = mContext;
        this.mListPhoto = mListPhoto;
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_photo_slide, container, false);

        ImageView imgphoto = view.findViewById(R.id.img1);

        String photo = mListPhoto.get(position);

        if(photo != null){
            Glide.with(mContext).load(photo).into(imgphoto);
        }

        //add view to viewgroup
        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        if(mListPhoto != null){
            return mListPhoto.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        //remove view
        container.removeView((View) object);

    }
}
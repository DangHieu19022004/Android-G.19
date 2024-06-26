// Generated by view binder compiler. Do not edit!
package com.example.appdocsach.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.appdocsach.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentNewsBinding implements ViewBinding {
  @NonNull
  private final DrawerLayout rootView;

  @NonNull
  public final ConstraintLayout cons1;

  @NonNull
  public final DrawerLayout drawerlayout;

  @NonNull
  public final FrameLayout frame1;

  @NonNull
  public final LinearLayout frame2;

  @NonNull
  public final FrameLayout frameLayoutNew;

  @NonNull
  public final TabItem item0;

  @NonNull
  public final TabItem item1;

  @NonNull
  public final TabItem item2;

  @NonNull
  public final TabItem item3;

  @NonNull
  public final TabItem item4;

  @NonNull
  public final TabItem itemall;

  @NonNull
  public final NavigationView navigationView;

  @NonNull
  public final LinearProgressIndicator progressBar;

  @NonNull
  public final RecyclerView recycleviewItems;

  @NonNull
  public final EditText searchEdit;

  @NonNull
  public final TabLayout tabLayout;

  @NonNull
  public final Toolbar toolbar;

  private FragmentNewsBinding(@NonNull DrawerLayout rootView, @NonNull ConstraintLayout cons1,
      @NonNull DrawerLayout drawerlayout, @NonNull FrameLayout frame1, @NonNull LinearLayout frame2,
      @NonNull FrameLayout frameLayoutNew, @NonNull TabItem item0, @NonNull TabItem item1,
      @NonNull TabItem item2, @NonNull TabItem item3, @NonNull TabItem item4,
      @NonNull TabItem itemall, @NonNull NavigationView navigationView,
      @NonNull LinearProgressIndicator progressBar, @NonNull RecyclerView recycleviewItems,
      @NonNull EditText searchEdit, @NonNull TabLayout tabLayout, @NonNull Toolbar toolbar) {
    this.rootView = rootView;
    this.cons1 = cons1;
    this.drawerlayout = drawerlayout;
    this.frame1 = frame1;
    this.frame2 = frame2;
    this.frameLayoutNew = frameLayoutNew;
    this.item0 = item0;
    this.item1 = item1;
    this.item2 = item2;
    this.item3 = item3;
    this.item4 = item4;
    this.itemall = itemall;
    this.navigationView = navigationView;
    this.progressBar = progressBar;
    this.recycleviewItems = recycleviewItems;
    this.searchEdit = searchEdit;
    this.tabLayout = tabLayout;
    this.toolbar = toolbar;
  }

  @Override
  @NonNull
  public DrawerLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentNewsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentNewsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_news, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentNewsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.cons1;
      ConstraintLayout cons1 = ViewBindings.findChildViewById(rootView, id);
      if (cons1 == null) {
        break missingId;
      }

      DrawerLayout drawerlayout = (DrawerLayout) rootView;

      id = R.id.frame1;
      FrameLayout frame1 = ViewBindings.findChildViewById(rootView, id);
      if (frame1 == null) {
        break missingId;
      }

      id = R.id.frame2;
      LinearLayout frame2 = ViewBindings.findChildViewById(rootView, id);
      if (frame2 == null) {
        break missingId;
      }

      id = R.id.frame_layout_new;
      FrameLayout frameLayoutNew = ViewBindings.findChildViewById(rootView, id);
      if (frameLayoutNew == null) {
        break missingId;
      }

      id = R.id.item0;
      TabItem item0 = ViewBindings.findChildViewById(rootView, id);
      if (item0 == null) {
        break missingId;
      }

      id = R.id.item1;
      TabItem item1 = ViewBindings.findChildViewById(rootView, id);
      if (item1 == null) {
        break missingId;
      }

      id = R.id.item2;
      TabItem item2 = ViewBindings.findChildViewById(rootView, id);
      if (item2 == null) {
        break missingId;
      }

      id = R.id.item3;
      TabItem item3 = ViewBindings.findChildViewById(rootView, id);
      if (item3 == null) {
        break missingId;
      }

      id = R.id.item4;
      TabItem item4 = ViewBindings.findChildViewById(rootView, id);
      if (item4 == null) {
        break missingId;
      }

      id = R.id.itemall;
      TabItem itemall = ViewBindings.findChildViewById(rootView, id);
      if (itemall == null) {
        break missingId;
      }

      id = R.id.navigation_view;
      NavigationView navigationView = ViewBindings.findChildViewById(rootView, id);
      if (navigationView == null) {
        break missingId;
      }

      id = R.id.progress_bar;
      LinearProgressIndicator progressBar = ViewBindings.findChildViewById(rootView, id);
      if (progressBar == null) {
        break missingId;
      }

      id = R.id.recycleview_items;
      RecyclerView recycleviewItems = ViewBindings.findChildViewById(rootView, id);
      if (recycleviewItems == null) {
        break missingId;
      }

      id = R.id.search_edit;
      EditText searchEdit = ViewBindings.findChildViewById(rootView, id);
      if (searchEdit == null) {
        break missingId;
      }

      id = R.id.tabLayout;
      TabLayout tabLayout = ViewBindings.findChildViewById(rootView, id);
      if (tabLayout == null) {
        break missingId;
      }

      id = R.id.toolbar;
      Toolbar toolbar = ViewBindings.findChildViewById(rootView, id);
      if (toolbar == null) {
        break missingId;
      }

      return new FragmentNewsBinding((DrawerLayout) rootView, cons1, drawerlayout, frame1, frame2,
          frameLayoutNew, item0, item1, item2, item3, item4, itemall, navigationView, progressBar,
          recycleviewItems, searchEdit, tabLayout, toolbar);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
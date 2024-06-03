// Generated by view binder compiler. Do not edit!
package com.example.appdocsach.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.appdocsach.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityLoginBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button Login;

  @NonNull
  public final TextView SignUp;

  @NonNull
  public final ImageView facebook;

  @NonNull
  public final Button forget;

  @NonNull
  public final ImageView google;

  @NonNull
  public final ImageView imageView15;

  @NonNull
  public final LinearLayout linearLayout;

  @NonNull
  public final LinearLayout linearLayout3;

  @NonNull
  public final ConstraintLayout main;

  @NonNull
  public final EditText password;

  @NonNull
  public final TextView textView9;

  @NonNull
  public final EditText username;

  private ActivityLoginBinding(@NonNull ConstraintLayout rootView, @NonNull Button Login,
      @NonNull TextView SignUp, @NonNull ImageView facebook, @NonNull Button forget,
      @NonNull ImageView google, @NonNull ImageView imageView15, @NonNull LinearLayout linearLayout,
      @NonNull LinearLayout linearLayout3, @NonNull ConstraintLayout main,
      @NonNull EditText password, @NonNull TextView textView9, @NonNull EditText username) {
    this.rootView = rootView;
    this.Login = Login;
    this.SignUp = SignUp;
    this.facebook = facebook;
    this.forget = forget;
    this.google = google;
    this.imageView15 = imageView15;
    this.linearLayout = linearLayout;
    this.linearLayout3 = linearLayout3;
    this.main = main;
    this.password = password;
    this.textView9 = textView9;
    this.username = username;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_login, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityLoginBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.Login;
      Button Login = ViewBindings.findChildViewById(rootView, id);
      if (Login == null) {
        break missingId;
      }

      id = R.id.SignUp;
      TextView SignUp = ViewBindings.findChildViewById(rootView, id);
      if (SignUp == null) {
        break missingId;
      }

      id = R.id.facebook;
      ImageView facebook = ViewBindings.findChildViewById(rootView, id);
      if (facebook == null) {
        break missingId;
      }

      id = R.id.forget;
      Button forget = ViewBindings.findChildViewById(rootView, id);
      if (forget == null) {
        break missingId;
      }

      id = R.id.google;
      ImageView google = ViewBindings.findChildViewById(rootView, id);
      if (google == null) {
        break missingId;
      }

      id = R.id.imageView15;
      ImageView imageView15 = ViewBindings.findChildViewById(rootView, id);
      if (imageView15 == null) {
        break missingId;
      }

      id = R.id.linearLayout;
      LinearLayout linearLayout = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout == null) {
        break missingId;
      }

      id = R.id.linearLayout3;
      LinearLayout linearLayout3 = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout3 == null) {
        break missingId;
      }

      ConstraintLayout main = (ConstraintLayout) rootView;

      id = R.id.password;
      EditText password = ViewBindings.findChildViewById(rootView, id);
      if (password == null) {
        break missingId;
      }

      id = R.id.textView9;
      TextView textView9 = ViewBindings.findChildViewById(rootView, id);
      if (textView9 == null) {
        break missingId;
      }

      id = R.id.username;
      EditText username = ViewBindings.findChildViewById(rootView, id);
      if (username == null) {
        break missingId;
      }

      return new ActivityLoginBinding((ConstraintLayout) rootView, Login, SignUp, facebook, forget,
          google, imageView15, linearLayout, linearLayout3, main, password, textView9, username);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}

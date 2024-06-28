package com.example.appdocsach.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.appdocsach.MainActivity;
import com.example.appdocsach.R;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class Setting extends AppCompatActivity {
    FirebaseAuth auth;
    GoogleSignInClient gsc;
    GoogleSignInOptions gso;

    SharedPreferences sharedPreferences;
    ImageView logout, change, switchbtn, backbtn;
    TextView nightModeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setting);

        auth = FirebaseAuth.getInstance();

        mapping();

        sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE);
        boolean isNightMode = sharedPreferences.getBoolean("NightMode", false);
        updateNightModeUI(isNightMode);

        switchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleNightMode();
            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Setting.this, ForgetPassActivity.class));
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        // Set up Google sign-in client
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);


    }

    private void mapping() {
        logout = findViewById(R.id.logout);
        change = findViewById(R.id.change);
        switchbtn = findViewById(R.id.switchbtn);
        backbtn = findViewById(R.id.backarrowSetting);
        nightModeTextView = findViewById(R.id.night_mode);
    }

    private void toggleNightMode() {
        boolean isNightMode = sharedPreferences.getBoolean("NightMode", false);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (isNightMode) {
            // Switch to day mode
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            editor.putBoolean("NightMode", false);
        } else {
            // Switch to night mode
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            editor.putBoolean("NightMode", true);
        }

        editor.apply();
        updateNightModeUI(!isNightMode);
    }

    private void updateNightModeUI(boolean isNightMode) {
        if (isNightMode) {
            switchbtn.setImageResource(R.drawable.nightmode);
            nightModeTextView.setText("Chế độ ban đêm");
        } else {
            switchbtn.setImageResource(R.drawable.daymode);
            nightModeTextView.setText("Chế độ ban ngày");
        }
    }

    // Method to sign out from Google account
    private void signOut() {
        // Log out from Facebook
        LoginManager.getInstance().logOut();

        // Log out from Google
        gsc.signOut()
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Log out from Firebase
                        auth.signOut();
                        // Sign out successful, navigate back to MainActivity
                        Intent intent = new Intent(Setting.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Sign out failed
                        Toast.makeText(Setting.this, "Đăng xuất không thành công", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
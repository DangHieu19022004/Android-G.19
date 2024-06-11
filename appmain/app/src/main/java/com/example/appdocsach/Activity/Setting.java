package com.example.appdocsach.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setting);

        auth = FirebaseAuth.getInstance();

        ImageView logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {signOut();}
        });

        // Set up Google sign-in client
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);


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
                        Toast.makeText(Setting.this, "Sign out failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
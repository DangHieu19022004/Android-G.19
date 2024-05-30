package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import org.json.JSONException;

public class SecondActivity extends AppCompatActivity {
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Get username and email from Intent
        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");
        String email = intent.getStringExtra("EMAIL");

        // Display username and email on TextViews
        TextView usernameTextView = findViewById(R.id.textView5);
        TextView emailTextView = findViewById(R.id.textView6);
        usernameTextView.setText(username);
        emailTextView.setText(email);

        //Graph API
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if(accessToken!=null && !accessToken.isExpired()) {
            GraphRequest request = GraphRequest.newMeRequest(
                    accessToken,
                    (object, response) -> {
                        // Application code
                        try {
                            assert object != null;
                            String name = object.getString("name");
                            usernameTextView.setText(name);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,link");
            request.setParameters(parameters);
            request.executeAsync();
        }
        // Set up sign out button
        Button logoutButton = findViewById(R.id.button2);
        logoutButton.setOnClickListener(v -> signOut());
        logoutButton.setOnClickListener(v ->{
            LoginManager.getInstance().logOut();
            startActivity(new Intent(SecondActivity.this, LoginActivity.class));
            finish();
        });

        // Set up Google sign-in client
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);
    }

    // Method to sign out from Google account
    private void signOut() {
        gsc.signOut()
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign out successful, navigate back to MainActivity
                        Intent intent = new Intent(SecondActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Sign out failed
                        Toast.makeText(SecondActivity.this, "Sign out failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
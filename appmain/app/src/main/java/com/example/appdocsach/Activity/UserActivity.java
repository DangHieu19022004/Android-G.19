//package com.example.appdocsach;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.appdocsach.Activity.Setting;
//import com.facebook.AccessToken;
//import com.facebook.GraphRequest;
//import com.google.android.gms.auth.api.signin.GoogleSignIn;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInClient;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//
//import org.json.JSONException;
//
//public class UserActivity extends AppCompatActivity {
//    GoogleSignInOptions gso;
//    GoogleSignInClient gsc;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_user);
//
//
//        // Get username and email from Intent
//        Intent intent = getIntent();
//        String username = intent.getStringExtra("USERNAME");
//        String email = intent.getStringExtra("EMAIL");
//
//        // Display username and email on TextViews
//        TextView usernameTextView = findViewById(R.id.username);
//        TextView emailTextView = findViewById(R.id.email);
//        usernameTextView.setText(username);
//        emailTextView.setText(email);
//
//
//        //Graph API facebook
//        AccessToken accessToken = AccessToken.getCurrentAccessToken();
//        if(accessToken!=null && !accessToken.isExpired()) {
//            GraphRequest request = GraphRequest.newMeRequest(
//                    accessToken,
//                    (object, response) -> {
//                        // Application code
//                        try {
//                            assert object != null;
//                            String name = object.getString("name");
//                            usernameTextView.setText(name);
//                        } catch (JSONException e) {
//                            throw new RuntimeException(e);
//                        }
//                    });
//            Bundle parameters = new Bundle();
//            parameters.putString("fields", "id,name,link");
//            request.setParameters(parameters);
//            request.executeAsync();
//        }
//
//        //Setting
//        ImageView setting = findViewById(R.id.setting);
//        setting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(UserActivity.this, Setting.class));
//                finish();
//            }
//        });
//    }
//}
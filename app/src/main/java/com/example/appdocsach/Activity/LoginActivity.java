package com.example.appdocsach.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appdocsach.MainActivity;
import com.example.appdocsach.R;
import com.example.appdocsach.Activity.SignUpActivity;
import com.example.appdocsach.model.User;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Collections;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText edUseremail, edPassword;
    ImageView fbBtn;
    CallbackManager callbackManager;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView googleBtn;
    DatabaseReference databaseReference;
    Button edForget;

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        //new code firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        edUseremail = findViewById(R.id.EmailAddress);
        edPassword = findViewById(R.id.password);
        edForget = findViewById(R.id.forget);

        edForget.setOnClickListener(v->{
            startActivity(new Intent(LoginActivity.this, ForgetPassActivity.class));
            finish();
        });

        Button loginButton = findViewById(R.id.Login);
        loginButton.setOnClickListener(v -> {
            String email = edUseremail.getText().toString();
            String pwd = edPassword.getText().toString();

            if (pwd.isEmpty() || email.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Vui lòng điền thông tin còn trống ", Toast.LENGTH_LONG).show();
            } else {
                mAuth.signInWithEmailAndPassword(email, pwd)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                    //new code
                                    if (firebaseUser != null) {
                                        DatabaseReference userRef = databaseReference.child(firebaseUser.getUid());
                                        //new code
                                        userRef.get().addOnCompleteListener(task1 -> {
                                            if (task1.isSuccessful()) {
                                                //old code
                                                String userName = task1.getResult().child("name").getValue(String.class);
                                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                intent.putExtra("USERNAME", userName);
                                                intent.putExtra("EMAIL", email);
                                                startActivity(intent);
                                                finish();
                                                //
                                            } else {
                                                Toast.makeText(LoginActivity.this, "Lưu thông tin thất bai: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                    //
                                } else {
                                    Toast.makeText(LoginActivity.this, "Quá trình xác thực đã thất bại: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        TextView text = findViewById(R.id.SignUp);
        text.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, SignUpActivity.class)));

        //Facebook
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Toast.makeText(LoginActivity.this, "Đăng nhập Facebook bị hủy", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull FacebookException error) {
                        // App code
                        Toast.makeText(LoginActivity.this, "Lỗi đăng nhập Facebook: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        fbBtn = findViewById(R.id.facebook);
        fbBtn.setOnClickListener(v -> {
            //login by facebook
            signInWithFacebook();
        });

        //Google
        googleBtn = findViewById(R.id.google);
        googleBtn.setOnClickListener(v -> signInWithGoogle());

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

    }
    // Login facebook
    void signInWithFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Collections.singletonList("public_profile"));
    }

    // Login google
    void signInWithGoogle() {
        gsc.signOut().addOnCompleteListener(this, task -> {
            Intent signInIntent = gsc.getSignInIntent();
            startActivityForResult(signInIntent, 1000);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        //Login facebook
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        //Login google
        if (requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("USERNAME", account.getDisplayName());
                    intent.putExtra("EMAIL", account.getEmail());
                    startActivity(intent);
                    finish();
                }
            } catch (ApiException e) {
                Log.w("SignIn", "signInResult:failed code=" + e.getStatusCode());
                Toast.makeText(getApplication(), "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
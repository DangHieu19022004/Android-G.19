package com.example.appdocsach.Activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appdocsach.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ForgetPassActivity extends AppCompatActivity {

    private EditText emailEditText, newPassEditText;
    private Button resetButton;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forget_pass);

        emailEditText = findViewById(R.id.EmailAddress);
        newPassEditText = findViewById(R.id.newpass);
        resetButton = findViewById(R.id.resetbtn);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        String email = emailEditText.getText().toString().trim();
        String newPassword = newPassEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(newPassword)) {
            Toast.makeText(ForgetPassActivity.this, "Vui lòng nhập email và mật khẩu mới", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null && user.getEmail().equals(email)) {
            user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        databaseReference.child(user.getUid()).child("password").setValue(newPassword);
                        Toast.makeText(ForgetPassActivity.this, "Mật khẩu đã được đặt lại", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ForgetPassActivity.this, "Lỗi khi đặt lại mật khẩu: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(ForgetPassActivity.this, "Email không khớp với tài khoản hiện tại", Toast.LENGTH_SHORT).show();
        }
    }
}

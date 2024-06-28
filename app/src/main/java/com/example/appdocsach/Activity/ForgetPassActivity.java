package com.example.appdocsach.Activity;

import android.content.Intent;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ForgetPassActivity extends AppCompatActivity {

    private EditText emailEditText;
    private Button resetButton;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forget_pass);

        emailEditText = findViewById(R.id.EmailAddress);
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

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(ForgetPassActivity.this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
            return;
        }

        databaseReference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String userId = userSnapshot.getKey();
                        // Reset password via email
                        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ForgetPassActivity.this, "Đã gửi email đặt lại mật khẩu", Toast.LENGTH_SHORT).show();
                                    // Redirect to LoginActivity
                                    Intent intent = new Intent(ForgetPassActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(ForgetPassActivity.this, "Lỗi khi gửi email đặt lại mật khẩu: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                } else {
                    Toast.makeText(ForgetPassActivity.this, "Không tìm thấy email này trong hệ thống", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ForgetPassActivity.this, "Lỗi khi tìm kiếm người dùng: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}


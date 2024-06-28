package com.example.appdocsach.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appdocsach.MainActivity;
import com.example.appdocsach.R;
import com.example.appdocsach.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText edUseremail, edPassword, edUserName;
    TextView edDate;
    Button btnRegister, btnGoLogin;
    DatePickerDialog.OnDateSetListener mDateSetListener;

    //new code
    DatabaseReference databaseReference;

    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        //new code
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");


        edUseremail = findViewById(R.id.edUseremail);
        edPassword = findViewById(R.id.edPassword);
        edDate = findViewById(R.id.edDate);
        edUserName = findViewById(R.id.edName);
        btnRegister = findViewById(R.id.SignUp);
        btnGoLogin = findViewById(R.id.btnGoLogin);

        edDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        SignUpActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener, year, month, day
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = month + "/" + dayOfMonth + "/" + year;
                edDate.setText(date);
            }
        };

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edUseremail.getText().toString();
                String pwd = edPassword.getText().toString();
                String user = edUserName.getText().toString();
                String created_date = edDate.getText().toString();

                if (user.isEmpty() || pwd.isEmpty() || email.isEmpty() || created_date.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Vui lòng nhập tất cả các trường bắt buộc", Toast.LENGTH_LONG).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(email, pwd)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SignUpActivity.this, "Tài khoản được tạo thành công.", Toast.LENGTH_SHORT).show();
                                        FirebaseUser currentUser = mAuth.getCurrentUser();
                                        //new code
                                        if (currentUser != null) {
                                            String userId = currentUser.getUid();
                                            User newUser = new User(user, email, created_date);
                                            databaseReference.child(userId).setValue(newUser).addOnCompleteListener(task1-> {
                                                if (task1.isSuccessful()) {
                                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                    intent.putExtra("USERNAME", user);
                                                    intent.putExtra("EMAIL", email);
                                                    startActivity(intent);
                                                    finish();
                                                } else {
                                                    Toast.makeText(SignUpActivity.this, "Không lưu được dữ liệu người dùng: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                        //
                                    } else {
                                        Toast.makeText(SignUpActivity.this, "Quá trình xác thực đã thất bại: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        btnGoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }
}

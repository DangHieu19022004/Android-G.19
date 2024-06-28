package com.example.appdocsach.Activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appdocsach.BroadcastReceiver.Internet;
import com.example.appdocsach.R;

public class StartActivity extends AppCompatActivity {

    private Internet internetBroadcastReceiver;
    private boolean isReceiverRegistered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_start);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        this.deleteDatabase("books.db");

        Button start = findViewById(R.id.button3);
        start.setOnClickListener(v->{
            Intent intent = new Intent(StartActivity.this, LoginActivity.class);
            startActivity(intent);

        });
    }
    @Override
    protected void onResume() {
        super.onResume();

        // Đăng ký BroadcastReceiver khi Activity được hiển thị
        if (!isReceiverRegistered) {
            registerReceiver(internetBroadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            isReceiverRegistered = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Hủy đăng ký BroadcastReceiver khi Activity không còn hiển thị
        try {
            if (isReceiverRegistered) {
                unregisterReceiver(internetBroadcastReceiver);
                isReceiverRegistered = false;
            }
        } catch (Exception e){
        }
    }
}
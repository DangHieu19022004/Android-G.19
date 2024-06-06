package com.example.appdocsach.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.TextToSpeech.Engine;
import android.util.Log;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdocsach.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class TextToSpeechActivity extends AppCompatActivity {

    private static final int CODE = 100;

    private TextToSpeech textToSpeech;
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tts);

        VideoView videoView = findViewById(R.id.videoView);

        // Đường dẫn của video trong thư mục raw
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.tts;

        // Set đường dẫn cho VideoView
        videoView.setVideoURI(Uri.parse(videoPath));
        videoView.start();
        // Thiết lập lắng nghe sự kiện khi video hoàn thành
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // Khi video hoàn thành, chơi lại từ đầu
                videoView.start();
            }
        });

        // Khởi tạo TextToSpeech để đọc văn bản từ Firebase
        textToSpeech = new TextToSpeech(this, new OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    // Thiết lập ngôn ngữ và các thuộc tính khác
                    textToSpeech.setLanguage(Locale.ENGLISH);
                    textToSpeech.setPitch(0.8f);
                    textToSpeech.setSpeechRate(1.0f);
                    // Lắng nghe dữ liệu từ Firebase và đọc văn bản
                    listenToFirebase();
                } else {
                    Toast.makeText(TextToSpeechActivity.this, "Khởi tạo TextToSpeech thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Phương thức để lắng nghe dữ liệu từ Firebase và đọc văn bản
    private void listenToFirebase() {
        // Khởi tạo Firebase Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("books");

        // Lắng nghe dữ liệu từ Firebase
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot bookSnapshot : dataSnapshot.getChildren()) {
                    String content = bookSnapshot.child("content").getValue(String.class);
                    // Phát âm văn bản từ Firebase
                    textToSpeech.speak(content, TextToSpeech.QUEUE_ADD, null, null);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Firebase", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Giải phóng TextToSpeech khi không cần thiết
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}

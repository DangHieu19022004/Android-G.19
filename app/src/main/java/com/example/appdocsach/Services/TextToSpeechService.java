package com.example.appdocsach.Services;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appdocsach.Activity.ReadBookActivity;
import com.example.appdocsach.MainActivity;
import com.example.appdocsach.R;

import java.util.Locale;

public class TextToSpeechService extends AppCompatActivity {

    private static final int CODE = 100;

    private TextToSpeech textToSpeech;
    private String textToSpeak; // Lưu trữ nội dung sách để đọc
    private TextView textContent; // TextView để hiển thị nội dung sách

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_book);

        // Lấy dữ liệu sách từ intent
        Intent intent = getIntent();
        if (intent != null) {
            textToSpeak = intent.getStringExtra("textToSpeak");
        }

        // Ánh xạ TextView để hiển thị nội dung sách
        textContent = findViewById(R.id.text_content);

        // Hiển thị nội dung sách
        if (textToSpeak != null && !textToSpeak.isEmpty()) {
            textContent.setText(textToSpeak);
        } else {
            textContent.setText("Không có nội dung sách để hiển thị");
        }

        // Kiểm tra và cài đặt dữ liệu TTS nếu chưa có
        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                // Dữ liệu TTS đã có sẵn, khởi tạo TextToSpeech với ngôn ngữ Tiếng Việt
                textToSpeech = new TextToSpeech(this, new OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if (status == TextToSpeech.SUCCESS) {
                            int result = textToSpeech.setLanguage(new Locale("vi", "VN"));
                            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                Log.e("TextToSpeech", "Ngôn ngữ không được hỗ trợ");
                                Toast.makeText(TextToSpeechService.this, "Ngôn ngữ không được hỗ trợ", Toast.LENGTH_SHORT).show();
                            } else {
                                // Thiết lập các thuộc tính khác của TextToSpeech
                                textToSpeech.setPitch(0.8f);
                                textToSpeech.setSpeechRate(1.0f);

                                // Bắt đầu đọc nội dung sách
                                startTextToSpeech();
                            }
                        } else {
                            Toast.makeText(TextToSpeechService.this, "Khởi tạo TextToSpeech thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                // Dữ liệu TTS chưa có, yêu cầu cài đặt
                Intent installIntent = new Intent();
                installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
                finish();
            }
        }
    }

    // Phương thức để bắt đầu Text-to-Speech
    private void startTextToSpeech() {
        if (textToSpeech != null && textToSpeak != null && !textToSpeak.isEmpty()) {
            // Bắt đầu đọc nội dung sách
            textToSpeech.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            Toast.makeText(this, "Không có nội dung sách để đọc", Toast.LENGTH_SHORT).show();
        }
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

package com.example.appdocsach;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class TranslateActivity extends AppCompatActivity {

    private TextView textContent;
    private com.example.appdocsach.TranslateService translateService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_book);

        textContent = findViewById(R.id.text_content);
        translateService = new com.example.appdocsach.TranslateService(this);
    }

    // Phương thức để dịch văn bản khi người dùng nhấn vào nút discIcontransBook
    public void translateText(View view) {
        String originalText = textContent.getText().toString();
        String translatedText = translateService.translateText(originalText, "en", "vi");

        if (translatedText != null) {
            textContent.setText(translatedText);
            Toast.makeText(this, "Đã dịch thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Lỗi khi dịch", Toast.LENGTH_SHORT).show();
        }
    }
}

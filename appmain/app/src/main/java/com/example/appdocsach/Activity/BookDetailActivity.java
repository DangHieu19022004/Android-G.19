package com.example.appdocsach.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appdocsach.R;

public class BookDetailActivity extends AppCompatActivity {

    ImageView threeDotsButton;
    Button btnstartreadDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mapping();

        // click 3 dots to show up menu options
        threeDotsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupmenu();
            }
        });

        //click to read book, intent to send info book to next activity
        btnstartreadDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // this is demo. need title to show book!!!
                Intent it = new Intent(BookDetailActivity.this, ReadBookActivity.class);
                startActivity(it);
            }
        });
    }

    private void showPopupmenu() {
        PopupMenu popupMenu = new PopupMenu(this, threeDotsButton);
        popupMenu.getMenuInflater().inflate(R.menu.menu_three_detailbook, popupMenu.getMenu());
        popupMenu.show();
    }
    private void mapping(){
        threeDotsButton = findViewById(R.id.threeDotsButton);
        btnstartreadDetail = findViewById(R.id.btnstartreadDetail);
    }
}
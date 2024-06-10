package com.example.appdocsach.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appdocsach.R;

public class BookDetailActivity extends AppCompatActivity {

    ImageView threeDotsButton;
    Button btnstartreadDetail;
    LinearLayout likeDetail, dislikeDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_detail);

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

        // Xử lý sự kiện khi nhấn nút like và dislike
        likeDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BookDetailActivity.this, "Liked!", Toast.LENGTH_SHORT).show();
                changeButtonColor(likeDetail);
            }
        });

        dislikeDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BookDetailActivity.this, "Disliked!", Toast.LENGTH_SHORT).show();
                changeButtonColor(dislikeDetail);
            }
        });
    }

    //Đổi màu khi nhấn nút
    private void changeButtonColor(LinearLayout buttonLayout) {
        // Khai báo phần image
        ImageView imageView = (ImageView) buttonLayout.getChildAt(0);

        // Đổi màu
        imageView.setColorFilter(getResources().getColor(R.color.colorGreen));
    }

    private void showPopupmenu() {
        PopupMenu popupMenu = new PopupMenu(this, threeDotsButton);
        popupMenu.getMenuInflater().inflate(R.menu.menu_three_detailbook, popupMenu.getMenu());
        popupMenu.show();
    }
    private void mapping(){
        threeDotsButton = findViewById(R.id.threeDotsButton);
        btnstartreadDetail = findViewById(R.id.btnstartreadDetail);
        likeDetail = findViewById(R.id.likeDetail);
        dislikeDetail = findViewById(R.id.dislikeDetail);
    }
}
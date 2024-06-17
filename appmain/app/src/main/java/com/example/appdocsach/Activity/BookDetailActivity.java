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
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.appdocsach.Fragment.options.HomeFragment;
import com.example.appdocsach.MainActivity;
import com.example.appdocsach.R;
import com.example.appdocsach.model.BooksModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BookDetailActivity extends AppCompatActivity {

    ImageView threeDotsButton, imgDetailBook, backButton;
    TextView likeDetailCount, dislikeDetailCount, subtitleDetailBook;
    Button btnstartreadDetail;
    LinearLayout likeDetail, dislikeDetail;
    private FirebaseDatabase database;
    private BooksModel currentBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_detail);

        database = FirebaseDatabase.getInstance();

        mapping();

        // show book clicked
        getInfoBookclick();

        // back homepage
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(BookDetailActivity.this, MainActivity.class);
                startActivity(it);
            }
        });

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
                int currentLikes = currentBook.getLike();
                currentBook.setLike(currentLikes + 1);
                // Update Firebase with the new likeCount
                updateLikeCountInFirebase(currentBook.getId(), currentBook.getLike());
            }
        });

        dislikeDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BookDetailActivity.this, "Disliked!", Toast.LENGTH_SHORT).show();
                changeButtonColor(dislikeDetail);
                int currentLikes = currentBook.getLike();
                currentBook.setLike(Math.max(currentLikes - 1, 0)); // Ensure likes don't go negative
                // Update Firebase with the new likeCount
                updateLikeCountInFirebase(currentBook.getId(), currentBook.getLike());
            }
        });
    }

    private void getInfoBookclick() {
        BooksModel book = (BooksModel) getIntent().getSerializableExtra("book_data");
        if (book != null) {
            Glide.with(this.imgDetailBook.getContext())
                    .load(book.getImg())
                    .into(this.imgDetailBook);
            likeDetailCount.setText(String.valueOf(book.getLike()));
            dislikeDetailCount.setText(String.valueOf(book.getDislikeCount()));
            subtitleDetailBook.setText(book.getSubtitle());

            // Lưu sách hiện tại
            currentBook = book;
        }
    }

    private void updateLikeCountInFirebase(String bookId, int likeCount) {
        DatabaseReference booksRef = database.getReference("books").child(bookId).child("likeCount");
        booksRef.setValue(likeCount)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Handle successful update (if needed)
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle failed update
                        Toast.makeText(BookDetailActivity.this, "Failed to update like count: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    //Đổi màu khi nhấn nút
    private void changeButtonColor(LinearLayout buttonLayout) {
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
        //Hieu - mapping click book
        imgDetailBook = findViewById(R.id.imgDetailBook);
        likeDetailCount = findViewById(R.id.likeDetailCount);
        dislikeDetailCount = findViewById(R.id.dislikeDetailCount);
        subtitleDetailBook = findViewById(R.id.subtitleDetailBook);
        backButton = findViewById(R.id.backButton);

        currentBook = new BooksModel();
        currentBook.setLike(0);
    }
}
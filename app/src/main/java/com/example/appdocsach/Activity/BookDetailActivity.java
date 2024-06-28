package com.example.appdocsach.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.appdocsach.DatabaseHelper;
import com.example.appdocsach.Fragment.options.HomeFragment;
import com.example.appdocsach.Fragment.options.SavedBookFragment;
import com.example.appdocsach.MainActivity;
import com.example.appdocsach.R;
import com.example.appdocsach.model.BooksModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BookDetailActivity extends AppCompatActivity {
    DatabaseHelper db;
    ImageView threeDotsButton, imgDetailBook, backButton, downloadButton;
    TextView subtitleDetailBook, headTextDetailBook, ViewCount, authorDetail;
    Button btnstartreadDetail;
    LinearLayout likeDetail, dislikeDetail;
    TextView txtLikeCount, txtDislikeCount;
    private FirebaseDatabase database;
    private BooksModel currentBook;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_detail);

        database = FirebaseDatabase.getInstance();


        mapping();

        // show book clicked
        getInfoBookclick();

        //click to save into book
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });

        // back homepage
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(BookDetailActivity.this, MainActivity.class);
                startActivity(it);
            }
        });

        // Click 3 dots to show up menu options

        threeDotsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupmenu();
            }
        });

        // Click to read book, intent to send info book to next activity
        btnstartreadDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // this is demo. need title to show book!!!
                Intent it = new Intent(BookDetailActivity.this, ReadBookActivity.class);
                it.putExtra("book_content", currentBook);
                startActivity(it);
            }
        });

        // Xử lý sự kiện khi nhấn nút like và dislike
        likeDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLikeButton();
            }
        });

        dislikeDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDislikeButton();
            }
        });

        // Load initial like and dislike counts from Firebase
        loadLikeAndDislikeCounts();
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(BookDetailActivity.this);
        builder.setTitle("Thông báo");
        builder.setMessage("Tải sách về thiết bị?");
        builder.setPositiveButton("OK", (dialog, which) -> {
            // Show progress dowload
            progressDialog = ProgressDialog.show(BookDetailActivity.this, "Đang tải", "Vui lòng chờ...", true);
            savetoDevice();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void savetoDevice() {

        new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            runOnUiThread(() -> {
                if(db.addBook(currentBook) == -1){
                    Toast.makeText(BookDetailActivity.this, "Lưu thất bại, vui lòng thử lại sau", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(BookDetailActivity.this, "Lưu thành công", Toast.LENGTH_SHORT).show();
                    SavedBookFragment.booksAdapterManage.notifyDataSetChanged(); // reset recycle view savedbook
                }
                // Tắt ProgressDialog sau khi lưu xong
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            });
        }).start();

    }

    private void loadLikeAndDislikeCounts() {
        DatabaseReference bookRef = database.getReference("books").child(currentBook.getId());
        bookRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    currentBook = snapshot.getValue(BooksModel.class);
                    updateLikeDislikeCounts();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BookDetailActivity.this, "Không thể tải chi tiết sách: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateLikeDislikeCounts() {
        txtLikeCount.setText(String.valueOf(currentBook.getLikeCount()));
        txtDislikeCount.setText(String.valueOf(currentBook.getDislikeCount()));
    }

    private void handleLikeButton() {
        Toast.makeText(BookDetailActivity.this, "Đã thích!", Toast.LENGTH_SHORT).show();
        changeButtonColor(likeDetail);
        int currentLikes = currentBook.getLikeCount();
        currentBook.setLikeCount(currentLikes + 1);
        updateLikeCountInFirebase(currentBook.getId(), currentBook.getLikeCount());
    }

    private void handleDislikeButton() {
        Toast.makeText(BookDetailActivity.this, "Không thích!", Toast.LENGTH_SHORT).show();
        changeButtonColor(dislikeDetail);
        int currentDislikes = currentBook.getDislikeCount();
        currentBook.setDislikeCount(Math.max(currentDislikes + 1, 0)); // Ensure dislikes don't go negative
        updateDislikeCountInFirebase(currentBook.getId(), currentBook.getDislikeCount());
    }

    private void getInfoBookclick() {
        BooksModel book = (BooksModel) getIntent().getSerializableExtra("book_data");
        if (book != null) {
            Glide.with(this.imgDetailBook.getContext())
                    .load(book.getImg())
                    .into(this.imgDetailBook);
            txtLikeCount.setText(String.valueOf(book.getLikeCount()));
            txtDislikeCount.setText(String.valueOf(book.getDislikeCount()));
            headTextDetailBook.setText(String.valueOf(book.getTitle()));
            ViewCount.setText(String.valueOf(book.getView()));
            authorDetail.setText(String.valueOf(book.getAuthor()));
            subtitleDetailBook.setText(String.valueOf(book.getSubtitle()));

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
                        updateLikeDislikeCounts(); // Update counts after successful update
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(BookDetailActivity.this, "Không cập nhật được số lượt thích: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateDislikeCountInFirebase(String bookId, int dislikeCount) {
        DatabaseReference booksRef = database.getReference("books").child(bookId).child("dislikeCount");
        booksRef.setValue(dislikeCount)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        updateLikeDislikeCounts(); // Update counts after successful update
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(BookDetailActivity.this, "Không cập nhật được số lượt không thích: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Đổi màu khi nhấn nút
    private void changeButtonColor(LinearLayout buttonLayout) {
        ImageView imageView = (ImageView) buttonLayout.getChildAt(0);
        imageView.setColorFilter(getResources().getColor(R.color.colorBule));
    }

    private void showPopupmenu() {
        PopupMenu popupMenu = new PopupMenu(this, threeDotsButton);
        popupMenu.getMenuInflater().inflate(R.menu.menu_three_detailbook, popupMenu.getMenu());
        popupMenu.show();
    }

    private void mapping() {
        threeDotsButton = findViewById(R.id.threeDotsButton);
        btnstartreadDetail = findViewById(R.id.btnstartreadDetail);
        likeDetail = findViewById(R.id.likeDetail);
        dislikeDetail = findViewById(R.id.dislikeDetail);

        //Hieu - mapping click book
        imgDetailBook = findViewById(R.id.imgDetailBook);

        subtitleDetailBook = findViewById(R.id.subtitleDetailBook);
        headTextDetailBook = findViewById(R.id.headTextDetailBook);
        ViewCount = findViewById(R.id.ViewCount);
        authorDetail = findViewById(R.id.authorDetail);
        backButton = findViewById(R.id.backButton);
        downloadButton = findViewById(R.id.downloadButton);


        txtLikeCount = findViewById(R.id.txtLikeCount);
        txtDislikeCount = findViewById(R.id.txtDislikeCount);

        // declare dtb
        db = new DatabaseHelper(this);

        // Initialize current book object
        currentBook = new BooksModel();
        currentBook.setLikeCount(0);
        currentBook.setDislikeCount(0);
    }
}
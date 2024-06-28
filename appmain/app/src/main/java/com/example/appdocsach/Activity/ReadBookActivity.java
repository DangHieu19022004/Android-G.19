package com.example.appdocsach.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appdocsach.MainActivity;
import com.example.appdocsach.R;
import com.example.appdocsach.model.BooksModel;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import android.view.View;
import android.widget.Toast;


public class ReadBookActivity extends AppCompatActivity {
    private FirebaseDatabase database;
    private BooksModel book;

    private ImageView backButtonReadBook, downloadButtonReadBook, discIconReadBook;
    private TextView textContent, textPageNumber;
    private Button btnPrevious, btnNext;
    private ScrollView scrollView;
    private String bookContent;

    private String bookId, bookImage, bookTitle, bookAuthor, bookDate;

    private int currentPage = 0;
    private int pageSize = 800; // Số ký tự mỗi trang (số ký tự bạn có thể thay đổi tùy vào nhu cầu)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_book);

        // mapping
        mapping();

        backButtonReadBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // get content book from detailbook
        book = (BooksModel) getIntent().getSerializableExtra("book_content");
        bookContent = book.getContent();

        // Cập nhật sách được đọc gần đây nhất
        updateRecentlyReadBooks(book);

        // show content
        displayPage(currentPage);

//<<<<<<< HEAD
//        // Get book details from the intent
//        bookId = book.getId();
//        bookImage = book.getImg();
//        bookTitle = book.getTitle();
//        bookAuthor = book.getAuthor();
//        bookDate = book.getDay();
//
//        // Save the recently read book info
//        saveRecentlyReadBook();
    }
//    private void saveRecentlyReadBook() {
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null) {
//            String userId = user.getUid();
//            DatabaseReference userBooksRef = FirebaseDatabase.getInstance().getReference("Users/" + userId + "/readBooks");
//
//            userBooksRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    if (!snapshot.exists()) {
//                        // Create the path if it doesn't exist
//                        userBooksRef.setValue("");
//                    }
//
//                    // Save the recently read book info
//                    BooksModel book = new BooksModel();
//                    book.setId(bookId);
//                    book.setImg(bookImage);
//                    book.setTitle(bookTitle);
//                    book.setAuthor(bookAuthor);
//                    book.setDay(bookDate);
//                    book.setTimestamp(System.currentTimeMillis());
//
//                    userBooksRef.child(bookId).setValue(book);
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                    // Handle possible errors.
//                }
//            });
//        }
//    }
//=======
    private void updateRecentlyReadBooks(BooksModel book) {
        // Lấy ID người dùng hiện tại
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Thêm thông tin sách đã đọc vào Firebase Database
        DatabaseReference userBooksRef = FirebaseDatabase.getInstance().getReference("Users/" + userId + "/readBooks/" + book.getId());
        userBooksRef.setValue(System.currentTimeMillis()).addOnSuccessListener(aVoid -> {
            Log.d("ReadBookActivity", "Book read time updated successfully.");
        }).addOnFailureListener(e -> {
            Log.e("ReadBookActivity", "Failed to update book read time.", e);
        });
    }

    private void mapping() {
        backButtonReadBook = findViewById(R.id.backButtonReadBook);

        discIconReadBook = findViewById(R.id.discIconReadBook);
        textContent = findViewById(R.id.text_content);
        btnPrevious = findViewById(R.id.btn_previous);
        btnNext = findViewById(R.id.btn_next);
        scrollView = findViewById(R.id.scroll_view);
        textPageNumber = findViewById(R.id.textPageNumber);
    }

    private void displayPage(int page) {
        int start = page * pageSize;
        int end = Math.min(start + pageSize, bookContent.length());

        String pageContent = bookContent.substring(start, end);
        textContent.setText(pageContent);

        // update count page present
        int currentPageNumber = page + 1; // page = 0
        int totalPages = calculateTotalPages(bookContent, pageSize);
        String pageNumberText = currentPageNumber + " / " + totalPages;
        textPageNumber.setText(pageNumberText);

        // scroll to top  page
        scrollView.scrollTo(0, 0);

        // update state button
        btnPrevious.setEnabled(page > 0);
        btnNext.setEnabled(end < bookContent.length());
    }

    private int calculateTotalPages(String content, int pageSize) {
        int contentLength = content.length();
        int totalPages = (int) Math.ceil((double) contentLength / pageSize);
        return totalPages;
    }


    // click button previous
    public void showPreviousPage(View view) {

        if (currentPage > 0) {
            currentPage--;

            displayPage(currentPage);
        }
    }

    // click button next
    public void showNextPage(View view) {
        int start = (currentPage + 1) * pageSize;

        if (start < bookContent.length()) {
            currentPage++;
            displayPage(currentPage);
        }
    }
}
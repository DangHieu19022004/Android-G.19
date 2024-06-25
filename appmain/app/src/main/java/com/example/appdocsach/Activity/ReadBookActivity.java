package com.example.appdocsach.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appdocsach.R;
import com.example.appdocsach.model.BooksModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReadBookActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private String bookId, bookImage, bookTitle, bookAuthor, bookDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_read_book);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get book details from the intent
        Intent intent = getIntent();
        if (intent != null) {
            bookId = intent.getStringExtra("Id");
            bookImage = intent.getStringExtra("Image");
            bookTitle = intent.getStringExtra("Title");
            bookAuthor = intent.getStringExtra("Author");
            bookDate = intent.getStringExtra("Date");

            // Save the recently read book info
            saveRecentlyReadBook();
        }
    }

    private void saveRecentlyReadBook() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            DatabaseReference userBooksRef = FirebaseDatabase.getInstance().getReference("users/" + userId + "/readBooks");

            userBooksRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.exists()) {
                        // Create the path if it doesn't exist
                        userBooksRef.setValue("");
                    }

                    // Save the recently read book info
                    BooksModel book = new BooksModel();
                    book.setId(bookId);
                    book.setImg(bookImage);
                    book.setTitle(bookTitle);
                    book.setAuthor(bookAuthor);
                    book.setDay(bookDate);
                    book.setTimestamp(System.currentTimeMillis());

                    userBooksRef.child(bookId).setValue(book);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle possible errors.
                }
            });
        }
    }

}
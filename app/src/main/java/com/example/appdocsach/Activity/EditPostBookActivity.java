package com.example.appdocsach.Activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.appdocsach.MainActivity;
import com.example.appdocsach.R;
import com.example.appdocsach.model.BooksModel;
import com.example.appdocsach.model.NewBookToPost;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.time.LocalDate;
import java.util.UUID;

public class EditPostBookActivity extends AppCompatActivity {

    private TextView destroyEditbook, chooseEditPicture;
    private Button btnEditBook;
    private Uri uri;
    private Spinner spinnerCategoryEdit;
    private EditText ContentEdit, subtitleEdit, titleEdit;
    private ImageView imgeditbook;

    private BooksModel booksModel;
    private NewBookToPost newBookToPost;
    private String imageURL,  selectedCategory, author;
    int dislikeCount, likeCount, view;

    private static final int PICK_IMAGE_REQUEST = 1;

    //declare category
    String[] categories = {"Khoa học", "Tâm lý", "Tiểu thuyết", "Ngoại ngữ", "Nấu ăn"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_post_book);

        mapping();

        //underline choose picture
        SpannableString spannableString = new SpannableString(chooseEditPicture.getText());
        spannableString.setSpan(new UnderlineSpan(), 0, 20, 0);
        chooseEditPicture.setText(spannableString);

        //get info to edit
        getInfoBookclick();

        //choose category
        setupspinnercategories();

        destroyEditbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        chooseEditPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Chọn ảnh"), PICK_IMAGE_REQUEST);
            }
        });

        spinnerCategoryEdit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = categories[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                String selectedCategory = categories[0];

            }
        });

        btnEditBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pushdatatoRealtimedtb();

            }
        });
    }

    private void pushdatatoRealtimedtb() {
        initializationBook();
        DatabaseReference newbook = FirebaseDatabase.getInstance().getReference("books/" + newBookToPost.getId() );
        newbook.setValue(newBookToPost, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null) {
                    Toast.makeText(EditPostBookActivity.this, "Chỉnh sửa thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditPostBookActivity.this, "Chỉnh sửa thất bại: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initializationBook() {
        String author = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String content = ContentEdit.getText().toString().trim();
        String day = getcurrentDay();
        int dislikeCount = booksModel.getDislikeCount();
        String id = booksModel.getId();
        String img = imageURL;
        int likeCount = booksModel.getLikeCount();
        String subtitle = subtitleEdit.getText().toString().trim();
        String title = titleEdit.getText().toString().trim();
        String type = selectedCategory;
        int view = booksModel.getView();

        newBookToPost = new NewBookToPost(author, content, day, dislikeCount, id, img, likeCount, subtitle, title, type, view);
    }

    private String getcurrentDay() {
        LocalDate currentDate = LocalDate.now();

        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();
        int day = currentDate.getDayOfMonth();

        return day + "/" + month + "/" + year;
    }

    private void setupspinnercategories() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoryEdit.setAdapter(adapter);
    }


    private void getInfoBookclick() {
        BooksModel book = (BooksModel) getIntent().getSerializableExtra("book_data");
        if (book != null) {

            Glide.with(this.imgeditbook.getContext())
                    .load(book.getImg())
                    .into(this.imgeditbook);

            titleEdit.setText(String.valueOf(book.getTitle()));
            subtitleEdit.setText(String.valueOf(book.getSubtitle()));
            ContentEdit.setText(book.getContent());



            // Lưu sách hiện tại
            booksModel = book;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
                && data != null && data.getData() != null) {
            uri = data.getData();
        }
        // push image to Firebase Storage
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("Image/" + idRandom() + ".jpg");
        storageRef.putFile(uri)
                .addOnSuccessListener(taskSnapshot -> {
                    storageRef.getDownloadUrl().addOnSuccessListener(uri -> {

                        imageURL = uri.toString();

                        //show new img to view
                        Glide.with(this)
                                .load(imageURL)
                                .into(imgeditbook);

                    });
                })
                .addOnFailureListener(exception -> {
                    Toast.makeText(EditPostBookActivity.this, "Tải lên thất bại", Toast.LENGTH_SHORT).show();
                });
    }

    private String idRandom() {
        UUID uuid = UUID.randomUUID();
        String randomId = uuid.toString();
        return randomId;
    }

    private void mapping() {
        destroyEditbook = findViewById(R.id.destroyEditbook);
        chooseEditPicture = findViewById(R.id.chooseEditPicture);
        btnEditBook = findViewById(R.id.btnEditBook);
        spinnerCategoryEdit = findViewById(R.id.spinnerCategoryEdit);
        ContentEdit = findViewById(R.id.ContentEdit);
        subtitleEdit = findViewById(R.id.subtitleEdit);
        titleEdit = findViewById(R.id.titleEdit);
        imgeditbook = findViewById(R.id.imgeditbook);
    }
}
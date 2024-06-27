package com.example.appdocsach.Activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appdocsach.MainActivity;
import com.example.appdocsach.R;
import com.example.appdocsach.model.NewBookToPost;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.time.LocalDate;
import java.util.UUID;

public class PostBookActivity extends AppCompatActivity {

    private TextView destroyPostbook, choosePicture;
    private Button btnPostBook;
    private Uri uri;
    private Spinner spinnerCategoryPost;
    private EditText ContentPost, subtitlePost, titlePost;

    private NewBookToPost newBookToPost;
    private String imageURL,  selectedCategory;

    private static final int PICK_IMAGE_REQUEST = 1;

    //declare category
    String[] categories = {"Khoa học", "Tâm lý", "Tiểu thuyết", "Ngoại ngữ", "Nấu ăn"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_post_book);

        mapping();

        setupspinnercategories();
        
        destroyPostbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(PostBookActivity.this, MainActivity.class);
                startActivity(it);
            }
        });

        choosePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Chọn ảnh"), PICK_IMAGE_REQUEST);

            }
        });

        spinnerCategoryPost.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 selectedCategory = categories[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                String selectedCategory = categories[0];

            }
        });


        btnPostBook.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(PostBookActivity.this, "Lưu sách thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PostBookActivity.this, "Lưu sách thất bại: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initializationBook() {
        String author = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String content = ContentPost.getText().toString().trim();
        String day = getcurrentDay();
        int dislikeCount = 0;
        String id = idRandom();
        String img = imageURL;
        int likeCount = 0;
        String subtitle = subtitlePost.getText().toString().trim();
        String title = titlePost.getText().toString().trim();
        String type = selectedCategory;
        int view = 0;

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
        spinnerCategoryPost.setAdapter(adapter);
    }

    private String idRandom() {
        UUID uuid = UUID.randomUUID();
        String randomId = uuid.toString();
        return randomId;
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


                    });
                })
                .addOnFailureListener(exception -> {
                    Toast.makeText(PostBookActivity.this, "Tải lên thất bại", Toast.LENGTH_SHORT).show();
                });
    }


    private void mapping() {
        destroyPostbook = findViewById(R.id.destroyPostbook);
        choosePicture = findViewById(R.id.choosePicture);
        btnPostBook = findViewById(R.id.btnPostBook);
        spinnerCategoryPost = findViewById(R.id.spinnerCategoryPost);
        ContentPost = findViewById(R.id.ContentPost);
        subtitlePost = findViewById(R.id.subtitlePost);
        titlePost = findViewById(R.id.titlePost);
    }
}
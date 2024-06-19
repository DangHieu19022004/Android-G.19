package com.example.appdocsach.model;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private FirebaseFirestore db;
    private CollectionReference booksRef;

    public Database() {
        db = FirebaseFirestore.getInstance();
        booksRef = db.collection("books");
    }

    // Function to search books by title
    public void searchBooksByTitle(String searchText, final OnSearchCompleteListener listener) {
        List<BooksModel> filteredList = new ArrayList<>();
        String searchTextLower = searchText.toLowerCase();
        Query query = booksRef.orderBy("title");

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        BooksModel book = document.toObject(BooksModel.class);
                        // Check if the title contains any part of searchText (case-insensitive)
                        if (normalizeString(book.getTitle()).contains(normalizeString(searchTextLower))) {
                            filteredList.add(book);
                        }
                    }
                    listener.onSearchComplete(filteredList);
                } else {
                    listener.onSearchError(task.getException().getMessage());
                }
            }
        });
    }


    private String normalizeString(String s) {
        return Normalizer.normalize(s, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase();
    }

    // Interface for search completion listener
    public interface OnSearchCompleteListener {
        void onSearchComplete(List<BooksModel> books);
        void onSearchError(String error);
    }
}


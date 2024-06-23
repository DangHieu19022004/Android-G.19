package com.example.appdocsach.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private DatabaseReference booksRef;

    public Database() {
        booksRef = FirebaseDatabase.getInstance().getReference("books");
    }

    // Function to search books by title
    public void searchBooksByTitle(String searchText, final OnSearchCompleteListener listener) {
        List<BooksModel> filteredList = new ArrayList<>();
        String searchTextLower = normalizeString(searchText.toLowerCase());

        booksRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    BooksModel book = snapshot.getValue(BooksModel.class);
                    if (book != null && normalizeString(book.getTitle()).contains(searchTextLower)) {
                        filteredList.add(book);
                    }
                }
                listener.onSearchComplete(filteredList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onSearchError(databaseError.getMessage());
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


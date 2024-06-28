package com.example.appdocsach.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Database {

    private DatabaseReference databaseReference;

    public Database() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("books");
    }

    public interface OnSearchCompleteListener {
        void onSearchComplete(List<BooksModel> books);
        void onSearchError(String error);
    }

    public void searchBooksByTitle(String title, OnSearchCompleteListener listener) {
        databaseReference.orderByChild("title").startAt(title).endAt(title + "\uf8ff")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<BooksModel> books = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            BooksModel book = snapshot.getValue(BooksModel.class);
                            books.add(book);
                        }
                        listener.onSearchComplete(books);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        listener.onSearchError(databaseError.getMessage());
                    }
                });
    }
}
package com.example.appdocsach.Fragment.typebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdocsach.Activity.BookDetailActivity;
import com.example.appdocsach.Adapter.BooksAdapterVertical;
import com.example.appdocsach.R;
import com.example.appdocsach.model.BooksModel;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class NovelTypeFragment extends Fragment {

    private RecyclerView recyclerViewNovel;
    private BooksAdapterVertical booksAdapterNovel;
    private List<BooksModel> mListBookNovel;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_novel_type, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewNovel = view.findViewById(R.id.recyclerViewNovel);

        mListBookNovel = new ArrayList<>();
        booksAdapterNovel = new BooksAdapterVertical( mListBookNovel, new BooksAdapterVertical.IClickListener() {
            @Override
            public void onClickReadItemBook(BooksModel books) {
                showDetailBook(books);
                logSelectContentEvent(books);
            }
        });
        recyclerViewNovel.setAdapter(booksAdapterNovel);
        recyclerViewNovel.setLayoutManager(new GridLayoutManager(getContext(), 3));

        // Call method to get data from Firebase Realtime Database
        getListRealtimeNovel();
    }
    private void showDetailBook(BooksModel books) {
        Intent it = new Intent(getActivity(), BookDetailActivity.class);
        it.putExtra("book_data", books); ///make serialize
        startActivity(it);
    }
    private void getListRealtimeNovel() {
        DatabaseReference myRef = database.getReference("books");

        // Update the query condition to fetch books of category "Tiểu thuyết"
        Query query = myRef.orderByChild("type").equalTo("Tiểu thuyết");

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                BooksModel booksModel = snapshot.getValue(BooksModel.class);
                if (booksModel != null) {
                    mListBookNovel.add(booksModel);
                    booksAdapterNovel.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                BooksModel booksModel = snapshot.getValue(BooksModel.class);
                if (booksModel == null) return;

                for (int i = 0; i < mListBookNovel.size(); i++) {
                    if (booksModel.getId().equals(mListBookNovel.get(i).getId())) {
                        mListBookNovel.set(i, booksModel);
                        break;
                    }
                }
                booksAdapterNovel.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                BooksModel booksModel = snapshot.getValue(BooksModel.class);
                if (booksModel == null) return;

                mListBookNovel.removeIf(book -> book.getId().equals(booksModel.getId()));
                booksAdapterNovel.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // Not needed for this implementation
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }
    private void logSelectContentEvent(BooksModel books) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, String.valueOf(books.getId()));
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, books.getTitle());
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

}

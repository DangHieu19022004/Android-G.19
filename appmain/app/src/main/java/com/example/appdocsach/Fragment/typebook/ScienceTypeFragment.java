package com.example.appdocsach.Fragment.typebook;

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

import com.example.appdocsach.Adapter.BooksAdapterHorizontal;
import com.example.appdocsach.Adapter.BooksAdapterVertical;
import com.example.appdocsach.R;
import com.example.appdocsach.model.BooksModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class ScienceTypeFragment extends Fragment {
    private RecyclerView recyclerViewScience;
    private BooksAdapterVertical booksAdapterVerticalScience;
    List<BooksModel>  mListBookScience;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_science_type, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Mapping view
        recyclerViewScience = view.findViewById(R.id.recyclerViewScience);
        //

        //declare list book
        mListBookScience = new ArrayList<>();
        //

        //show to screen
        booksAdapterVerticalScience = new BooksAdapterVertical(mListBookScience, new BooksAdapterVertical.IClickListener() {
            @Override
            public void onClickReadItemBook(BooksModel books) {
                Toast.makeText(getContext(), "click", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerViewScience.setAdapter(booksAdapterVerticalScience);
        //

        //show to screen
        recyclerViewScience.setLayoutManager(new GridLayoutManager(getContext(), 2));
        //

        // call method get dtb
        getListRealtimeDTB();
    }

    private void getListRealtimeDTB() {
        DatabaseReference myRef = database.getReference("books");

        Query query = myRef.orderByChild("type").equalTo("Khoa học");
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                BooksModel booksModel = snapshot.getValue(BooksModel.class);
                if(booksModel != null){
                    mListBookScience.add(booksModel);

                    booksAdapterVerticalScience.setBooksList(mListBookScience); //reset adapter

                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                BooksModel booksModel = snapshot.getValue(BooksModel.class);
                if(booksModel == null || mListBookScience == null || mListBookScience.isEmpty()){return;}
                for(int i =0; i<mListBookScience.size(); i++){
                    if(booksModel.getId() == mListBookScience.get(i).getId()){
                        mListBookScience.set(i, booksModel);
                        break;
                    }
                }
                booksAdapterVerticalScience.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                BooksModel booksModel = snapshot.getValue(BooksModel.class);
                if(booksModel == null || mListBookScience == null || mListBookScience.isEmpty()){return;}
                for(int i =0; i<mListBookScience.size(); i++){
                    if(booksModel.getId() == mListBookScience.get(i).getId()){
                        mListBookScience.remove(mListBookScience.get(i));
                        break;
                    }
                }
                booksAdapterVerticalScience.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
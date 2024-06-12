package com.example.appdocsach.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appdocsach.R;
import com.example.appdocsach.model.BooksModel;
import com.google.firebase.firestore.auth.User;

import java.util.List;

public class BooksAdapter extends  RecyclerView.Adapter<BooksAdapter.BookViewHolder>{

    private List<BooksModel> mlistBooks;
    private IClickListener mInterfaceClickListener;

    public interface IClickListener{
        void onClickReadItemBook(BooksModel books);
    }
    public BooksAdapter(List<BooksModel> mlistBooks, IClickListener mInterfaceClickListener) {
        this.mlistBooks = mlistBooks;
        this.mInterfaceClickListener = mInterfaceClickListener;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.books_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        BooksModel booksModel = mlistBooks.get(position);
        if(booksModel == null){return;}

        Glide.with(holder.imageViewItem.getContext())
                .load(booksModel.getImg())
                .into(holder.imageViewItem);
        holder.title.setText(booksModel.getTitle());
        holder.view.setText(String.valueOf(booksModel.getView()));
        holder.gravlv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInterfaceClickListener.onClickReadItemBook(booksModel);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(mlistBooks != null){
            return mlistBooks.size();
        }
        return 0;
    }


    class BookViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageViewItem;
        private TextView title, view;
        private LinearLayout gravlv;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewItem = itemView.findViewById(R.id.book_image);
            title = itemView.findViewById(R.id.book_title);
            view = itemView.findViewById(R.id.book_views);
            gravlv = itemView.findViewById(R.id.gravItemBook);


        }
    }
}
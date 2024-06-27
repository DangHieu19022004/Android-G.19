package com.example.appdocsach.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appdocsach.R;
import com.example.appdocsach.model.BooksModel;

import java.util.List;

public class BooksAdapterManage extends  RecyclerView.Adapter<BooksAdapterManage.BookViewHolder>{

    private List<BooksModel> mlistBooks;
    private BooksAdapterManage.IClickListener mInterfaceClickListener;

    public interface IClickListener{
        void onClickReadItemBook(BooksModel books);
        void onClickDeleteItemBook(BooksModel books, int position);
    }
    public BooksAdapterManage(List<BooksModel> mlistBooks, BooksAdapterManage.IClickListener mInterfaceClickListener) {
        this.mlistBooks = mlistBooks;
        this.mInterfaceClickListener = mInterfaceClickListener;
    }
    public void setBooksList(List<BooksModel> booksList) {
        this.mlistBooks = booksList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public BooksAdapterManage.BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.books_item_manage, parent, false);
        return new BooksAdapterManage.BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksAdapterManage.BookViewHolder holder, int position) {
        BooksModel booksModel = mlistBooks.get(position);
        if(booksModel == null){return;}

        Glide.with(holder.imageViewItem.getContext())
                .load(booksModel.getImg())
                .into(holder.imageViewItem);


        Log.d("BooksAdapterManage", "Title: " + booksModel.getTitle());
        Log.d("BooksAdapterManage", "Like Count: " + booksModel.getLikeCount());
        Log.d("BooksAdapterManage", "Dislike Count: " + booksModel.getDislikeCount());

        holder.title.setText(booksModel.getTitle());

        holder.txtLikeCount_manage.setText(String.valueOf(booksModel.getLikeCount()) );

        holder.txtDislikeCount_manage.setText(String.valueOf(booksModel.getDislikeCount()));

        holder.trashBook_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mInterfaceClickListener.onClickDeleteItemBook(booksModel, position);
                }
            }
        });

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

        private ImageView imageViewItem, likeIcon_manage, dislikeIcon_manage, trashBook_manage;
        private TextView title, txtLikeCount_manage, txtDislikeCount_manage;
        private LinearLayout gravlv;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewItem = itemView.findViewById(R.id.ImageBook_manage);
            txtLikeCount_manage = itemView.findViewById(R.id.txtLikeCount_manage);
            txtDislikeCount_manage = itemView.findViewById(R.id.txtDislikeCount_manage);
            likeIcon_manage = itemView.findViewById(R.id.likeIcon_manage);
            dislikeIcon_manage = itemView.findViewById(R.id.discIconReadBook);

            title = itemView.findViewById(R.id.book_title_manage);

            trashBook_manage = itemView.findViewById(R.id.trashBook_manage);

            gravlv = itemView.findViewById(R.id.gravItemBook_manage);

        }
    }
}
package com.example.appdocsach.Adapter;

import android.content.Context;
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

public class BooksAdapterVertical extends  RecyclerView.Adapter<BooksAdapterVertical.BookViewHolder>{
    private Context context;
    private List<BooksModel> mlistBooks;
    private BooksAdapterVertical.IClickListener mInterfaceClickListener;

    public interface IClickListener{
        void onClickReadItemBook(BooksModel books);
    }
    public BooksAdapterVertical(Context context, List<BooksModel> mlistBooks, BooksAdapterVertical.IClickListener mInterfaceClickListener) {
        this.context = context;
        this.mlistBooks = mlistBooks;
        this.mInterfaceClickListener = mInterfaceClickListener;
    }
    public void setBooksList(List<BooksModel> booksList) {
        this.mlistBooks = booksList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.books_item_vertical, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksAdapterVertical.BookViewHolder holder, int position) {
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
        private TextView title, category, view;
        private LinearLayout gravlv;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewItem = itemView.findViewById(R.id.book_image_vertical);
            title = itemView.findViewById(R.id.book_title_vertical);
            category = itemView.findViewById(R.id.book_category_vertical);
            view = itemView.findViewById(R.id.book_views_vertical);
            gravlv = itemView.findViewById(R.id.gravItemBook_vertical);


        }
    }
}
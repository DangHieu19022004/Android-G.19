package com.example.appdocsach.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appdocsach.Activity.BookDetailActivity;
import com.example.appdocsach.R;
import com.example.appdocsach.model.BooksModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class RecentlyReadAdapter extends RecyclerView.Adapter<RecentlyReadAdapter.RecentlyReadViewHolder> {
    private Context mContext;
    private ArrayList<BooksModel> mListBooks;

    public RecentlyReadAdapter(Context mContext, ArrayList<BooksModel> mListBooks) {
        this.mContext = mContext;
        this.mListBooks = mListBooks;
    }

    public void setData(ArrayList<BooksModel> list) {
        this.mListBooks = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecentlyReadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View bookView = inflater.inflate(R.layout.item_book, parent, false);
        return new RecentlyReadViewHolder(bookView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentlyReadViewHolder holder, int position) {
        BooksModel book = mListBooks.get(position);
        if (book == null) {
            return;
        }
        holder.titleBook.setText(book.getTitle());
        Glide.with(mContext)
                .load(book.getImg())
                .into(holder.imgBook);
        holder.authorBook.setText(book.getAuthor());
        holder.dateBook.setText(book.getDay());

        // Định dạng và hiển thị timestamp dưới dạng ngày đọc gần đây
        long timestamp = book.getTimestamp();
        Date date = new Date(timestamp);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
        String formattedDate = dateFormat.format(date);
        holder.dateBook.setText(formattedDate);

        holder.cardViewBook.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, BookDetailActivity.class);
            intent.putExtra("Id", mListBooks.get(holder.getAdapterPosition()).getId());
            intent.putExtra("Image", mListBooks.get(holder.getAdapterPosition()).getImg());
            intent.putExtra("Title", mListBooks.get(holder.getAdapterPosition()).getTitle());
            intent.putExtra("Author", mListBooks.get(holder.getAdapterPosition()).getAuthor());
            intent.putExtra("Date", mListBooks.get(holder.getAdapterPosition()).getDay());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mListBooks.size();
    }

    public static class RecentlyReadViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgBook;
        private TextView titleBook;
        private CardView cardViewBook;
        private TextView authorBook;
        private TextView dateBook;

        public RecentlyReadViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBook = itemView.findViewById(R.id.imgBooks);
            titleBook = itemView.findViewById(R.id.title);
            cardViewBook = itemView.findViewById(R.id.cardViewArticle);
            authorBook = itemView.findViewById(R.id.author);
            dateBook = itemView.findViewById(R.id.date);
        }
    }
}


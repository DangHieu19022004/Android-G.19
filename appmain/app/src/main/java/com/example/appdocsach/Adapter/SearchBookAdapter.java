package com.example.appdocsach.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appdocsach.R;
import com.example.appdocsach.model.BooksModel;

import java.util.ArrayList;
import java.util.List;

public class SearchBookAdapter extends RecyclerView.Adapter<SearchBookAdapter.SearchBookViewHolder> {
    private Context mContext;
    private List<BooksModel> mListBooks;
    private IClickListener mInterfaceClickListener;

    public interface IClickListener {
        void onClickReadItemBook(BooksModel books);
    }

    public SearchBookAdapter(Context context, List<BooksModel> mListBooks, IClickListener mInterfaceClickListener) {
        this.mContext = context;
        this.mListBooks = mListBooks;
        this.mInterfaceClickListener = mInterfaceClickListener;
    }

    public void setData(List<BooksModel> list) {
        this.mListBooks = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View bookView = LayoutInflater.from(mContext).inflate(R.layout.item_book, parent, false);
        return new SearchBookViewHolder(bookView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchBookViewHolder holder, int position) {
        BooksModel book = mListBooks.get(position);
        if (book == null) {
            return;
        }

        holder.titleBook.setText(book.getTitle());
        Glide.with(mContext)
                .load(book.getImg())
                .into(holder.imgBook);
        holder.authorBook.setText(book.getAuthor());
        holder.dateBook.setText(formatDate(book.getDay()));

        holder.itemView.setOnClickListener(v -> {
            if (mInterfaceClickListener != null) {
                mInterfaceClickListener.onClickReadItemBook(book);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListBooks == null ? 0 : mListBooks.size();
    }

    public static class SearchBookViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgBook;
        private TextView titleBook;
        private TextView authorBook;
        private TextView dateBook;

        public SearchBookViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBook = itemView.findViewById(R.id.imgBooks);
            titleBook = itemView.findViewById(R.id.title);
            authorBook = itemView.findViewById(R.id.author);
            dateBook = itemView.findViewById(R.id.date);
        }
    }

    // Helper method to format date if needed
    private String formatDate(String dateString) {
        // Implement date formatting logic if required
        return dateString;
    }
}

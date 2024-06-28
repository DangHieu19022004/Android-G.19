package com.example.appdocsach;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.appdocsach.Adapter.BooksAdapterManage;
import com.example.appdocsach.model.BooksModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "books.db";
    private static final String TABLE_BOOKS = "books";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_AUTHOR = "author";
    private static final String COLUMN_CATEGORY_ID = "categoryId";
    private static final String COLUMN_CONTENT = "content";
    private static final String COLUMN_IMG = "img";
    private static final String COLUMN_SUBTITLE = "subtitle";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_VIEW_COUNT = "viewCount";
    private static final String COLUMN_LIKE_COUNT = "likeCount";
    private static final String COLUMN_DISLIKE_COUNT = "dislikeCount";
    private static final String COLUMN_DAY = "day";
    private static final String COLUMN_TIMESTAMP = "timestamp";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BOOKS_TABLE = "CREATE TABLE " + TABLE_BOOKS + "("
                + COLUMN_ID + " TEXT PRIMARY KEY,"
                + COLUMN_AUTHOR + " TEXT,"
                + COLUMN_CATEGORY_ID + " INTEGER,"
                + COLUMN_CONTENT + " TEXT,"
                + COLUMN_IMG + " TEXT,"
                + COLUMN_SUBTITLE + " TEXT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_VIEW_COUNT + " INTEGER,"
                + COLUMN_LIKE_COUNT + " INTEGER,"
                + COLUMN_DISLIKE_COUNT + " INTEGER,"
                + COLUMN_DAY + " TEXT,"
                + COLUMN_TIMESTAMP + " INTEGER"
                + ")";
        db.execSQL(CREATE_BOOKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
        onCreate(db);
    }

    // Add a new book
    public long addBook(BooksModel book) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, book.getId());
        values.put(COLUMN_AUTHOR, book.getAuthor());
        values.put(COLUMN_CATEGORY_ID, book.getCategoryId());
        values.put(COLUMN_CONTENT, book.getContent());
        values.put(COLUMN_IMG, book.getImg());
        values.put(COLUMN_SUBTITLE, book.getSubtitle());
        values.put(COLUMN_TITLE, book.getTitle());
        values.put(COLUMN_VIEW_COUNT, book.getView());
        values.put(COLUMN_LIKE_COUNT, book.getLikeCount());
        values.put(COLUMN_DISLIKE_COUNT, book.getDislikeCount());
        values.put(COLUMN_DAY, book.getDay());

        long check = db.insert(TABLE_BOOKS, null, values);
        db.close();

        return check;
    }

    // Get a single book by ID
    public BooksModel getBook(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BOOKS, new String[]{COLUMN_ID, COLUMN_AUTHOR, COLUMN_CATEGORY_ID, COLUMN_CONTENT,

                        COLUMN_IMG, COLUMN_SUBTITLE, COLUMN_TITLE, COLUMN_VIEW_COUNT, COLUMN_LIKE_COUNT, COLUMN_DISLIKE_COUNT, COLUMN_DAY},
                COLUMN_ID + "=?", new String[]{id}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        BooksModel book = new BooksModel(
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR)),
                cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMG)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUBTITLE)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_VIEW_COUNT)),
                cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_LIKE_COUNT)),
                cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DISLIKE_COUNT)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DAY))
        );
        cursor.close();
        return book;
    }

    // Get all books
    public List<BooksModel> getAllBooks() {
        List<BooksModel> bookList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_BOOKS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                BooksModel book = new BooksModel(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMG)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUBTITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_VIEW_COUNT)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_LIKE_COUNT)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DISLIKE_COUNT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DAY))
                );
                bookList.add(book);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return bookList;
    }

    // Update a book
    public int updateBook(BooksModel book) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_AUTHOR, book.getAuthor());
        values.put(COLUMN_CATEGORY_ID, book.getCategoryId());
        values.put(COLUMN_CONTENT, book.getContent());
        values.put(COLUMN_IMG, book.getImg());
        values.put(COLUMN_SUBTITLE, book.getSubtitle());
        values.put(COLUMN_TITLE, book.getTitle());
        values.put(COLUMN_VIEW_COUNT, book.getView());
        values.put(COLUMN_LIKE_COUNT, book.getLikeCount());
        values.put(COLUMN_DISLIKE_COUNT, book.getDislikeCount());
        values.put(COLUMN_DAY, book.getDay());

        return db.update(TABLE_BOOKS, values, COLUMN_ID + " = ?", new String[]{book.getId()});
    }

    // Delete a book
    public int deleteBook(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_BOOKS, COLUMN_ID + " = ?", new String[]{id});
        db.close();
        return result;
    }
}
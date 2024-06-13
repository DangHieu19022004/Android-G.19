package com.example.appdocsach.model;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private List<BooksModel> bookList;
    private List<category> categoryList;

    public Database() {
        this.bookList = new ArrayList<>();
        this.categoryList = new ArrayList<>();
    }

    // Hàm đọc dữ liệu từ tệp JSON
    public void loadBooksFromJson(InputStream inputStream) {
        try {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new InputStreamReader(inputStream));

            // Đọc dữ liệu vào đối tượng Database
            Database database = gson.fromJson(reader, Database.class);

            // Sao chép danh sách sách từ đối tượng Database vào danh sách của lớp hiện tại
            this.bookList = database.bookList;
            this.categoryList = database.categoryList;

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Hàm tìm kiếm sách dựa trên tiêu đề
    public List<BooksModel> searchBooksByTitle(String searchText) {
        List<BooksModel> filteredList = new ArrayList<>();
        for (BooksModel book : bookList) {
            if (book.getTitle().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(book);
            }
        }
        return filteredList;
    }

    // Hàm tìm kiếm sách dựa trên thể loại
    public List<BooksModel> getBooksByCategory(int categoryId) {
        List<BooksModel> filteredList = new ArrayList<>();
        for (BooksModel book : bookList) {
            if (book.getCategoryId() == categoryId) {
                filteredList.add(book);
            }
        }
        return filteredList;
    }

    // Getters và setters cho danh sách sách và danh sách thể loại
    public List<BooksModel> getBookList() {
        return bookList;
    }

    public void setBookList(List<BooksModel> bookList) {
        this.bookList = bookList;
    }

    public List<category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<category> categoryList) {
        this.categoryList = categoryList;
    }
}

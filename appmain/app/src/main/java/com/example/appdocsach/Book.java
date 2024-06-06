package com.example.appdocsach;

public class Book {
    private String author;
    private int categoryId;
    private String content;
    private String id;
    private String img;
    private long timestamp;
    private String title;
    private int view;

    public Book(String author, int categoryId, String content, String id, String img, long timestamp, String title, int view) {
        this.author = author;
        this.categoryId = categoryId;
        this.content = content;
        this.id = id;
        this.img = img;
        this.timestamp = timestamp;
        this.title = title;
        this.view = view;
    }

    public String getTitle() {
        return title;
    }
}

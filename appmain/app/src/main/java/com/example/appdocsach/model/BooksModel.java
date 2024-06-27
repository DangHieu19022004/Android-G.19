package com.example.appdocsach.model;

import java.io.Serializable;

public class BooksModel implements Serializable {

    private String author;
    private int categoryId;
    private String content;
    private String id;
    private String img;
    private String subtitle;
    private String title;
    private int view;
    private int likeCount;
    private int dislikeCount;
    private String day;


    public BooksModel(String author, int categoryId, String content, String id, String img, String subtitle,
                      String title, int view, int likeCount, int dislikeCount, String day) {
        this.author = author;
        this.categoryId = categoryId;
        this.content = content;
        this.id = id;
        this.img = img;
        this.subtitle = subtitle;
        this.title = title;
        this.view = view;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.day = day;
    }

    // Constructor for basic initialization
    public BooksModel() {
    }

    // Getters and setters for all fields
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(int dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
}

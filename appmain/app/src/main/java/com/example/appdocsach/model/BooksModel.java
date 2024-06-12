package com.example.appdocsach.model;

public class BooksModel {

    private String author;
    private int categoryId;
    private String content;
    private String id;
    private String img;
    private String subtitle;
    private String title;
    private int view;

    public BooksModel(String author, int categoryId, String content, String id, String img, String subtitle, String title, int view) {
        this.author = author;
        this.categoryId = categoryId;
        this.content = content;
        this.id = id;
        this.img = img;
        this.subtitle = subtitle;
        this.title = title;
        this.view = view;
    }

    public BooksModel() {
    }

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
}

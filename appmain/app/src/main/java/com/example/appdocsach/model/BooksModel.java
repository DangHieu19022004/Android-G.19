package com.example.appdocsach.model;

public class BooksModel {

    private String author;
    private String content;
    private String day;
    private String id;
    private String img;
    private String subtitle;
    private String title;
    private String type;
    private int view;

    public BooksModel() {
    }

    public BooksModel(String author, String content, String day, String id, String img, String subtitle, String title, String type, int view) {
        this.author = author;
        this.content = content;
        this.day = day;
        this.id = id;
        this.img = img;
        this.subtitle = subtitle;
        this.title = title;
        this.type = type;
        this.view = view;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }
}

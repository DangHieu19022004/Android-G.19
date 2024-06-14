package com.example.appdocsach.model;

import java.util.List;

public class BooksModel {

    private String author;
    private int categoryId;
    private String content;
    private String id;
    private List<String> imageUrls;
    private String img;
    private String subtitle;
    private String title;
    private int view;
    private int likeCount;

    public BooksModel(String author, int categoryId, String content, String id, List<String> imageUrls, String img, String subtitle, String title, int view, int likeCount) {
        this.author = author;
        this.categoryId = categoryId;
        this.content = content;
        this.id = id;
        this.imageUrls = imageUrls;
        this.img = img;
        this.subtitle = subtitle;
        this.title = title;
        this.view = view;
        this.likeCount = likeCount;
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
    public List<String> getImageUrls() {
        return imageUrls;
    }
    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
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
    public int getLike(){ return likeCount; }
    public void setLike(int likeCount) {this.likeCount = likeCount; }
}
package com.example.appdocsach.model;

import java.io.Serializable;

public class NewBookToPost implements Serializable {
    private String author;
    private String content;
    private String day;
    private int dislikeCount;
    private String id;
    private String img;
    private int likeCount;
    private String subtitle;
    private String title;
    private String type;
    private int view;

    public NewBookToPost() {
    }

    public NewBookToPost(String author, String content, String day, int dislikeCount, String id, String img, int likeCount, String subtitle, String title, String type, int view) {
        this.author = author;
        this.content = content;
        this.day = day;
        this.dislikeCount = dislikeCount;
        this.id = id;
        this.img = img;
        this.likeCount = likeCount;
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

    public int getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(int dislikeCount) {
        this.dislikeCount = dislikeCount;
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

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
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
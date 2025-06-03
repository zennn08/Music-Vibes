package com.example.musicvibes;

public class PostModel {
    String title, content, imageUrl, excerpt, publishDate;

    public PostModel(String title, String content, String excerpt, String imageUrl, String publishDate) {
        this.title = title;
        this.content = content;
        this.excerpt = excerpt;
        this.imageUrl = imageUrl;
        this.publishDate = publishDate;
    }
}
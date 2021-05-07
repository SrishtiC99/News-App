package com.example.newsapp;

public class News {
    private String title;
    private String sectionName;
    private String webUrl;
    private String authorName;
    private String publishedDate;

    public News(String title, String sectionName, String webUrl, String authorName, String publishedDate) {
        this.title = title;
        this.sectionName = sectionName;
        this.webUrl = webUrl;
        this.authorName = authorName;
        this.publishedDate = publishedDate;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getTitle() {
        return title;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getAuthorName() {
        return authorName;
    }
}

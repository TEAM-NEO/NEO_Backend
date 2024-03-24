package com.neo.needeachother.starpage.domain;

public class Article {
    private ArticleId articleId;
    private Image representativeImage;
    private Title title;
    private CategoryName categoryName;
    private Author author;
    private Like like;

    private boolean isContainImage(){
        return false;
    }
}

package com.neo.needeachother.starpage.domain.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class RepresentativeArticleHeadLine extends StarPageHeadLine {
    private final String author;
    private final String categoryTitle;
    private final String title;
    private final String representativeImage;
    private final String tapName;

    public RepresentativeArticleHeadLine(Long postId, int likeCount, String author, String categoryTitle,
                                         String categoryType, String title, String representativeImage, String tapName)
    {
        super(postId, likeCount, categoryType);
        this.author = author;
        this.categoryTitle = categoryTitle;
        this.title = title;
        this.representativeImage = representativeImage;
        this.tapName = tapName;
    }
}

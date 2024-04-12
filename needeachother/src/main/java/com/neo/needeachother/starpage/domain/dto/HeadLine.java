package com.neo.needeachother.starpage.domain.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class HeadLine {
    private final Long postId;
    private final String author;
    private final String categoryTitle;
    private final String categoryType;
    private final String title;
    private final int likeCount;
    private final String representativeImage;
}

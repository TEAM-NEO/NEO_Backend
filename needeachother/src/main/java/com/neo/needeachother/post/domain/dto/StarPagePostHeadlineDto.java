package com.neo.needeachother.post.domain.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@RequiredArgsConstructor
public class StarPagePostHeadlineDto {
    private final String categoryId;
    private final String categoryTitle;
    private final Long postId;
    private final int likeCount;
    private final String authorName;
    private final String postTitle;
    private final LocalDateTime createdAt;
    private final boolean hostHearted;
    private final String postType;
    private final String status;
    private final boolean isChanged;
}

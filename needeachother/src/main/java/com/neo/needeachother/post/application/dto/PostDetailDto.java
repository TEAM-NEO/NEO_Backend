package com.neo.needeachother.post.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDetailDto {

    private Long postId;
    private String categoryId;
    private String title;
    private String authorName;
    private String status;
    private int likeCount;
    private boolean hostHeart;
    private LocalDateTime exposureAt;
    private String postType;

    /* common & image post data */
    private String representativeImage;

    /* only common post data */
    private List<CommonPostParagraphDto> paragraph;

    /* GoldBalance, Vote post data */
    private String question;
    private Map<String, VoteAblePostOptionDetailDto> options;
}

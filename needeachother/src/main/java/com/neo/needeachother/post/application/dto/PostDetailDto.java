package com.neo.needeachother.post.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PostDetailDto {

    private Long postId;
    private String categoryId;
    private String title;
    private String authorName;
    private String status;
    private int likeCount;
    private boolean hostHeart;

    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime exposureAt;

    private String postType;

    /* common & image post data */
    private String representativeImage;

    /* only common post data */
    private List<CommonPostParagraphDto> paragraph;

    /* GoldBalance, Vote post data */
    private String question;

    @JsonProperty(value = "options")
    private Map<String, VoteAblePostOptionDetailDto> options;
}

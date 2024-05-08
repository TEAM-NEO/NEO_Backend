package com.neo.needeachother.post.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class StarPagePostHeadlineDto {

    private String categoryId;

    private String categoryTitle;

    private Long postId;

    private int likeCount;

    private String authorName;

    private String postTitle;

    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime createdAt;

    private boolean hostHearted;

    private String postType;

    private String status;

    private boolean isChanged;
}

package com.neo.needeachother.post.presentation.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

@Getter
@RequiredArgsConstructor
public class GetPostPageByCategoryRequest {
    private String categoryId;
    private Long lastPostId;
    private boolean orderByCreatedAt;
    private boolean orderByLikeCount;
    private boolean onlySearchHostWritten;
    private boolean onlySearchHostHearted;
}

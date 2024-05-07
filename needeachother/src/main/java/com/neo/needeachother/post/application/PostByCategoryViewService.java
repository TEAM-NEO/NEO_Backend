package com.neo.needeachother.post.application;

import com.neo.needeachother.category.domain.CategoryId;
import com.neo.needeachother.post.domain.dto.PostSearchCondition;
import com.neo.needeachother.post.domain.dto.StarPagePostHeadlineDto;
import com.neo.needeachother.post.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostByCategoryViewService {

    private final PostRepository postRepository;

    public Slice<StarPagePostHeadlineDto> getPostPageByCategoryWithInfiniteScroll(String categoryId, Long lastPostId, Pageable pageable,
                                                        boolean orderByCreatedAt, boolean orderByLikeCount, boolean onlySearchHostWritten, boolean onlySearchHostHearted) {

        return postRepository.searchPostHeadlineByCategoryIdWithNoOffset(
                CategoryId.of(categoryId),
                PostSearchCondition.of(orderByCreatedAt, orderByLikeCount, onlySearchHostWritten, onlySearchHostHearted),
                lastPostId,
                pageable);
    }

    public Page<StarPagePostHeadlineDto> getPostPageByCategoryWithPaging(String categoryId, Pageable pageable,
                                                                         boolean orderByCreatedAt, boolean orderByLikeCount, boolean onlySearchHostWritten, boolean onlySearchHostHearted) {
        return postRepository.searchPostHeadlineByCategoryIdWithPaging(
                CategoryId.of(categoryId),
                PostSearchCondition.of(orderByCreatedAt, orderByLikeCount, onlySearchHostWritten, onlySearchHostHearted),
                pageable
        );
    }
}

package com.neo.needeachother.post.domain.repository;

import com.neo.needeachother.category.domain.CategoryId;
import com.neo.needeachother.post.domain.AlbumPost;
import com.neo.needeachother.post.domain.CommonPost;
import com.neo.needeachother.post.domain.GoldBalancePost;
import com.neo.needeachother.post.domain.VotePost;
import com.neo.needeachother.post.domain.dto.PostSearchCondition;
import com.neo.needeachother.post.domain.dto.StarPagePostHeadlineDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.Optional;

public interface PostRepositoryCustom {
    Page<StarPagePostHeadlineDto> searchPostHeadlineByCategoryIdWithPaging(CategoryId categoryId, PostSearchCondition postSearchCondition, Pageable pageable);
    Slice<StarPagePostHeadlineDto> searchPostHeadlineByCategoryIdWithNoOffset(CategoryId categoryId, PostSearchCondition postSearchCondition, Long lastPostId, Pageable pageable);
    Optional<CommonPost> findCommonPostById(Long postId);
    Optional<AlbumPost> findAlbumPostById(Long postId);
    Optional<GoldBalancePost> findGoldBalancePostById(Long postId);
    Optional<VotePost> findVotePostById(Long postId);
}

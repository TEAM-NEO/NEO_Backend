package com.neo.needeachother.post.presentation;

import com.neo.needeachother.post.application.PostByCategoryViewService;
import com.neo.needeachother.post.application.PostDetailViewService;
import com.neo.needeachother.post.application.dto.PostDetailDto;
import com.neo.needeachother.post.domain.dto.StarPagePostHeadlineDto;
import com.neo.needeachother.post.presentation.dto.GetPostPageByCategoryRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostViewController {

    private final PostByCategoryViewService postByCategoryViewService;
    private final PostDetailViewService postDetailViewService;

    @GetMapping("/infinite")
    public ResponseEntity<Slice<StarPagePostHeadlineDto>> demandGetPostPageByCategoryWithInfiniteScroll(
            @Valid @RequestBody GetPostPageByCategoryRequest postPageByCategoryRequest,
            Pageable pageable) {

        Slice<StarPagePostHeadlineDto> foundPostSlice = postByCategoryViewService.getPostPageByCategoryWithInfiniteScroll(
                postPageByCategoryRequest.getCategoryId(),
                postPageByCategoryRequest.getLastPostId(),
                pageable,
                postPageByCategoryRequest.isOrderByCreatedAt(),
                postPageByCategoryRequest.isOrderByLikeCount(),
                postPageByCategoryRequest.isOnlySearchHostWritten(),
                postPageByCategoryRequest.isOnlySearchHostHearted()
        );

        return ResponseEntity.ok(foundPostSlice);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<StarPagePostHeadlineDto>> demandGetPostPageByCategoryWithPaging(
            @Valid @RequestBody GetPostPageByCategoryRequest postPageByCategoryRequest,
            Pageable pageable) {

        Page<StarPagePostHeadlineDto> foundPostPage = postByCategoryViewService.getPostPageByCategoryWithPaging(
                postPageByCategoryRequest.getCategoryId(),
                pageable,
                postPageByCategoryRequest.isOrderByCreatedAt(),
                postPageByCategoryRequest.isOrderByLikeCount(),
                postPageByCategoryRequest.isOnlySearchHostWritten(),
                postPageByCategoryRequest.isOnlySearchHostHearted()
        );

        return ResponseEntity.ok(foundPostPage);
    }

    @GetMapping("/{post_id}")
    public ResponseEntity<PostDetailDto> demandGetPostDetail(@PathVariable("post_id") Long postId, @RequestParam String type){
        PostDetailDto foundPostDetail = postDetailViewService.getPostDetailView(type, postId);
        return ResponseEntity.ok(foundPostDetail);
    }

}

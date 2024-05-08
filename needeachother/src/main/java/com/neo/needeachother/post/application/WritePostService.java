package com.neo.needeachother.post.application;

import com.neo.needeachother.category.domain.CategoryId;
import com.neo.needeachother.post.application.dto.PostDetailDto;
import com.neo.needeachother.post.domain.*;
import com.neo.needeachother.post.domain.repository.PostRepository;
import com.neo.needeachother.post.domain.domainservice.CreatePostByCategoryService;
import com.neo.needeachother.post.presentation.dto.CommonPostParagraphRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WritePostService {

    private final PostRepository postRepository;
    private final CreatePostByCategoryService createPostByCategoryService;

    @Transactional
    public PostDetailDto writeCommonPost(String title, String authorName, String authorEmail, String categoryId, List<CommonPostParagraphRequest> paragraphs) {
        CommonPost createAndSaveCommonPost = postRepository.save(
                createPostByCategoryService.createCommonPost(title, Author.of(authorName, authorEmail), CategoryId.of(categoryId),
                        paragraphs.stream()
                                .map(paragraph -> CommonPostParagraph.of(paragraph.getBody(), paragraph.getParagraphType()))
                                .toList()));

        return createAndSaveCommonPost.toPostDetailDto();
    }

    @Transactional
    public PostDetailDto writeAlbumPost(String title, String authorName, String authorEmail, String categoryId, String imagePath) {
        AlbumPost createAndSaveAlbumPost = postRepository.save(createPostByCategoryService.createAlbumPost(
                title, Author.of(authorName, authorEmail), AlbumImage.of(imagePath), CategoryId.of(categoryId)));

        return createAndSaveAlbumPost.toPostDetailDto();
    }

    @Transactional
    public PostDetailDto writeGoldBalancePost(String title, String authorName, String authorEmail, String categoryId, String question,
                                     String leftExample, String rightExample) {
        GoldBalancePost createAndSaveGoldBalancePost = postRepository.save(createPostByCategoryService.createGoldBalancePost(
                title, Author.of(authorName, authorEmail), question, leftExample, rightExample, CategoryId.of(categoryId)));

        return createAndSaveGoldBalancePost.toPostDetailDto();
    }

    @Transactional
    public PostDetailDto writeVotePost(String title, String authorName, String authorEmail, String categoryId, String question,
                              int timeToLive, List<String> voteOptionNameList) {

        VotePost createAndSaveVotePost = postRepository.save(createPostByCategoryService.createVotePost(
                title, Author.of(authorName, authorEmail), question, timeToLive, voteOptionNameList.stream()
                        .map(VoteItem::of)
                        .toList(), CategoryId.of(categoryId)));

        return createAndSaveVotePost.toPostDetailDto();
    }
}

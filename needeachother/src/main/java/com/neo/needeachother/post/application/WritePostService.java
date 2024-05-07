package com.neo.needeachother.post.application;

import com.neo.needeachother.category.domain.CategoryId;
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
    public void writeCommonPost(String title, String authorName, String authorEmail, String categoryId, List<CommonPostParagraphRequest> paragraphs) {
        CommonPost createdCommonPost = createPostByCategoryService.createCommonPost(
                title,
                Author.of(authorName, authorEmail),
                CategoryId.of(categoryId),
                paragraphs.stream()
                        .map(paragraph -> CommonPostParagraph.of(paragraph.getBody(), paragraph.getParagraphType()))
                        .toList());
        postRepository.save(createdCommonPost);
    }

    @Transactional
    public void writeAlbumPost(String title, String authorName, String authorEmail, String categoryId, String imagePath) {
        AlbumPost createdAlbumPost = createPostByCategoryService.createAlbumPost(
                title, Author.of(authorName, authorEmail), AlbumImage.of(imagePath), CategoryId.of(categoryId));
        postRepository.save(createdAlbumPost);
    }

    @Transactional
    public void writeGoldBalancePost(String title, String authorName, String authorEmail, String categoryId, String question,
                                     String leftExample, String rightExample) {
        GoldBalancePost createdGoldBalancePost = createPostByCategoryService.createGoldBalancePost(
                title, Author.of(authorName, authorEmail), question, leftExample, rightExample, CategoryId.of(categoryId));
        postRepository.save(createdGoldBalancePost);
    }

    @Transactional
    public void writeVotePost(String title, String authorName, String authorEmail, String categoryId, String question,
                              int timeToLive, List<String> voteOptionNameList) {

        List<VoteItem> voteItemList = voteOptionNameList.stream()
                .map(VoteItem::of)
                .toList();

        VotePost createdVotePost = createPostByCategoryService.createVotePost(
                title, Author.of(authorName, authorEmail), question, timeToLive, voteItemList, CategoryId.of(categoryId));

        postRepository.save(createdVotePost);
    }
}

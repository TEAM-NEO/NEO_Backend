package com.neo.needeachother.post.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostUseCaseImpl implements PostUseCase{

    private final HostHeartService hostHeartService;
    private final LikePostService likePostService;
    private final RestorePostService restorePostService;
    private final DeletePostService deletePostService;

    @Override
    public void giveHostHeart(Long postId, String email) {
        hostHeartService.giveHostHeart(postId, email);
    }

    @Override
    public void retrieveHostHeart(Long postId, String email) {
        hostHeartService.retrieveHostHeart(postId, email);
    }

    @Override
    public void likeIt(Long postId, String email) {
        likePostService.likeIt(postId, email);
    }

    @Override
    public void unLikeIt(Long postId, String email) {
        likePostService.unLikeIt(postId, email);
    }

    @Override
    public void restorePost(Long postId, String email) {
        restorePostService.restorePost(postId, email);
    }

    @Override
    public void deletePost(Long postId, String email) {
        deletePostService.deletePost(postId, email);
    }

    @Override
    public void posting(){

    }

}

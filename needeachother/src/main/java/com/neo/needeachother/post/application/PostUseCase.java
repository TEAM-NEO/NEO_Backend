package com.neo.needeachother.post.application;

public interface PostUseCase {
    void giveHostHeart(Long postId, String email);
    void retrieveHostHeart(Long postId, String email);
    void likeIt(Long postId, String email);
    void unLikeIt(Long postId, String email);
    void restorePost(Long postId, String email);
    void deletePost(Long postId, String email);
    void posting();
}

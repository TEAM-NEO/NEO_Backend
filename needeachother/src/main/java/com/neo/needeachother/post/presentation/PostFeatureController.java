package com.neo.needeachother.post.presentation;

import com.neo.needeachother.post.application.HostHeartService;
import com.neo.needeachother.post.application.LikePostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostFeatureController {

    private final HostHeartService hostHeartService;
    private final LikePostService likePostService;

    @PutMapping("/{post_id}/heart")
    public void demandGiveHostHeart(@PathVariable("post_id") Long postId, @RequestParam String email){
        hostHeartService.giveHostHeart(postId, email);
    }

    @PutMapping("{post_id}/unheart")
    public void demandRetrieveHostHeart(@PathVariable("post_id") Long postId, @RequestParam String email){
        hostHeartService.retrieveHostHeart(postId, email);
    }

    @PutMapping("/{post_id}/like")
    public void demandLikeThisPost(@PathVariable("post_id") Long postId, @RequestParam String email){
        likePostService.likeIt(postId, email);
    }

    @PutMapping("/{post_id}/unlike")
    public void demandCancelLikeThisPost(@PathVariable("post_id") Long postId, @RequestParam String email){
        likePostService.unLikeIt(postId, email);
    }
}

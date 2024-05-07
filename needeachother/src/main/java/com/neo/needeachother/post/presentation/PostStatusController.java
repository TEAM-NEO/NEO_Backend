package com.neo.needeachother.post.presentation;

import com.neo.needeachother.post.application.DeletePostService;
import com.neo.needeachother.post.application.RestorePostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostStatusController {

    private final DeletePostService deletePostService;
    private final RestorePostService restorePostService;

    @DeleteMapping("/{post_id}")
    public void demandDeletePost(@PathVariable("post_id") Long postId, @RequestParam String email){
        deletePostService.deletePost(postId, email);
    }

    @PutMapping("/{post_id}")
    public void demandRestorePost(@PathVariable("post_id") Long postId, @RequestParam String email){
        restorePostService.restorePost(postId, email);
    }
}

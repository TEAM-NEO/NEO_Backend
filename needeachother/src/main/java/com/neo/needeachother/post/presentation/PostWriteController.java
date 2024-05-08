package com.neo.needeachother.post.presentation;

import com.neo.needeachother.post.application.WritePostService;
import com.neo.needeachother.post.application.dto.PostDetailDto;
import com.neo.needeachother.post.presentation.dto.WriteAlbumPostRequest;
import com.neo.needeachother.post.presentation.dto.WriteCommonPostRequest;
import com.neo.needeachother.post.presentation.dto.WriteGoldBalancePostRequest;
import com.neo.needeachother.post.presentation.dto.WriteVotePostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostWriteController {

    private final WritePostService writePostService;

    @PostMapping("/common")
    public ResponseEntity<PostDetailDto> demandWriteCommonPost(@RequestBody WriteCommonPostRequest writeCommonPostRequest){
        PostDetailDto resultPostDetail = writePostService.writeCommonPost(
                writeCommonPostRequest.getTitle(),
                writeCommonPostRequest.getAuthorName(),
                writeCommonPostRequest.getAuthorEmail(),
                writeCommonPostRequest.getCategoryId(),
                writeCommonPostRequest.getParagraphs()
        );

        return ResponseEntity.created(URI.create("/api/v1/post/" + resultPostDetail.getPostId()))
                .body(resultPostDetail);
    }

    @PostMapping("/album")
    public ResponseEntity<PostDetailDto> demandWriteAlbumPost(@RequestBody WriteAlbumPostRequest writeAlbumPostRequest){
        PostDetailDto resultPostDetail = writePostService.writeAlbumPost(
                writeAlbumPostRequest.getTitle(),
                writeAlbumPostRequest.getAuthorName(),
                writeAlbumPostRequest.getAuthorEmail(),
                writeAlbumPostRequest.getCategoryId(),
                writeAlbumPostRequest.getImagePath()
        );

        return ResponseEntity.created(URI.create("/api/v1/post/" + resultPostDetail.getPostId()))
                .body(resultPostDetail);
    }

    @PostMapping("/gold_balance")
    public ResponseEntity<PostDetailDto> demandWriteGoldBalancePost(@RequestBody WriteGoldBalancePostRequest writeGoldBalancePostRequest){
        PostDetailDto resultPostDetail = writePostService.writeGoldBalancePost(
                writeGoldBalancePostRequest.getTitle(),
                writeGoldBalancePostRequest.getAuthorName(),
                writeGoldBalancePostRequest.getAuthorEmail(),
                writeGoldBalancePostRequest.getCategoryId(),
                writeGoldBalancePostRequest.getQuestion(),
                writeGoldBalancePostRequest.getLeftExample(),
                writeGoldBalancePostRequest.getRightExample()
        );

        return ResponseEntity.created(URI.create("/api/v1/post/" + resultPostDetail.getPostId()))
                .body(resultPostDetail);
    }

    @PostMapping("/vote")
    public ResponseEntity<PostDetailDto> demandWriteVotePost(@RequestBody WriteVotePostRequest writeVotePostRequest){
        PostDetailDto resultPostDetail = writePostService.writeVotePost(
                writeVotePostRequest.getTitle(),
                writeVotePostRequest.getAuthorName(),
                writeVotePostRequest.getAuthorEmail(),
                writeVotePostRequest.getCategoryId(),
                writeVotePostRequest.getQuestion(),
                writeVotePostRequest.getTimeToLive(),
                writeVotePostRequest.getOptionTextList()
        );

        return ResponseEntity.created(URI.create("/api/v1/post/" + resultPostDetail.getPostId()))
                .body(resultPostDetail);
    }
}

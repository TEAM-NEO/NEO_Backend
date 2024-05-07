package com.neo.needeachother.post.presentation;

import com.neo.needeachother.post.application.WritePostService;
import com.neo.needeachother.post.domain.CommonPostParagraph;
import com.neo.needeachother.post.presentation.dto.WriteAlbumPostRequest;
import com.neo.needeachother.post.presentation.dto.WriteCommonPostRequest;
import com.neo.needeachother.post.presentation.dto.WriteGoldBalancePostRequest;
import com.neo.needeachother.post.presentation.dto.WriteVotePostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostWriteController {

    private final WritePostService writePostService;

    @PostMapping("/common")
    public void demandWriteCommonPost(@RequestBody WriteCommonPostRequest writeCommonPostRequest){
        writePostService.writeCommonPost(
                writeCommonPostRequest.getTitle(),
                writeCommonPostRequest.getAuthorName(),
                writeCommonPostRequest.getAuthorEmail(),
                writeCommonPostRequest.getCategoryId(),
                writeCommonPostRequest.getParagraphs()
        );
    }

    @PostMapping("/album")
    public void demandWriteAlbumPost(@RequestBody WriteAlbumPostRequest writeAlbumPostRequest){
        writePostService.writeAlbumPost(
                writeAlbumPostRequest.getTitle(),
                writeAlbumPostRequest.getAuthorName(),
                writeAlbumPostRequest.getAuthorEmail(),
                writeAlbumPostRequest.getCategoryId(),
                writeAlbumPostRequest.getImagePath()
        );
    }

    @PostMapping("/gold_balance")
    public void demandWriteGoldBalancePost(@RequestBody WriteGoldBalancePostRequest writeGoldBalancePostRequest){
        writePostService.writeGoldBalancePost(
                writeGoldBalancePostRequest.getTitle(),
                writeGoldBalancePostRequest.getAuthorName(),
                writeGoldBalancePostRequest.getAuthorEmail(),
                writeGoldBalancePostRequest.getCategoryId(),
                writeGoldBalancePostRequest.getQuestion(),
                writeGoldBalancePostRequest.getLeftExample(),
                writeGoldBalancePostRequest.getRightExample()
        );
    }

    @PostMapping("/vote")
    public void demandWriteVotePost(@RequestBody WriteVotePostRequest writeVotePostRequest){
        writePostService.writeVotePost(
                writeVotePostRequest.getTitle(),
                writeVotePostRequest.getAuthorName(),
                writeVotePostRequest.getAuthorEmail(),
                writeVotePostRequest.getCategoryId(),
                writeVotePostRequest.getQuestion(),
                writeVotePostRequest.getTimeToLive(),
                writeVotePostRequest.getOptionTextList()
        );
    }
}

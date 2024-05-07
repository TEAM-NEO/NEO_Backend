package com.neo.needeachother.post.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class WriteVotePostRequest {
    private String title;
    private String authorName;
    private String authorEmail;
    private String categoryId;
    private String question;
    private Integer timeToLive;
    private List<String> optionTextList;
}

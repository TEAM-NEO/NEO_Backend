package com.neo.needeachother.post.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
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

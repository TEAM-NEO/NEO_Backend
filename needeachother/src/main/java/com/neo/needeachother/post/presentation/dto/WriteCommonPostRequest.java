package com.neo.needeachother.post.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class WriteCommonPostRequest {
    private String title;
    private String authorName;
    private String authorEmail;
    private String categoryId;
    private List<CommonPostParagraphRequest> paragraphs;
}
package com.neo.needeachother.post.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WriteCommonPostRequest {
    private String title;
    private String authorName;
    private String authorEmail;
    private String categoryId;

    @JsonProperty(value = "paragraphs")
    private List<CommonPostParagraphRequest> paragraphs;
}
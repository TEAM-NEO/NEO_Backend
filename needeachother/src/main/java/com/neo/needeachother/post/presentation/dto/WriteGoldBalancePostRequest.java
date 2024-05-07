package com.neo.needeachother.post.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class WriteGoldBalancePostRequest {
    private String title;
    private String authorName;
    private String authorEmail;
    private String categoryId;
    private String question;
    private String leftExample;
    private String rightExample;
}

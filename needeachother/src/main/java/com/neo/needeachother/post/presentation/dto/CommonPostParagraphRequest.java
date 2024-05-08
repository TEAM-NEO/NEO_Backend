package com.neo.needeachother.post.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonPostParagraphRequest {
    private String paragraphType;
    private String body;
}

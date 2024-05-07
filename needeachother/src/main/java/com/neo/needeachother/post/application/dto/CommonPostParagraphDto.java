package com.neo.needeachother.post.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CommonPostParagraphDto {
    private String paragraphType;
    private String body;
}

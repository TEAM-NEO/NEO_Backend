package com.neo.needeachother.post.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VoteAblePostOptionDetailDto {
    private String optionName;
    private Integer optionCount;
    private Integer optionRate;
}

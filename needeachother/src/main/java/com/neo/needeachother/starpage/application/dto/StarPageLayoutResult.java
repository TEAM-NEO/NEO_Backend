package com.neo.needeachother.starpage.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class StarPageLayoutResult {
    private Long layoutId;
    private String layoutTitle;
    private String layoutType;
    private String categoryId;
}

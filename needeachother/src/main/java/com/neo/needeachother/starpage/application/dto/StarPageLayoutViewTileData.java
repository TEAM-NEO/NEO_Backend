package com.neo.needeachother.starpage.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@Builder
public class StarPageLayoutViewTileData {
    private Long postId;
    private int likeCount;
    private String categoryType;
    private String categoryTitle;
    private String author;
    private String title;
    private String representativeImage;
    private String question;
    private String leftExample;
    private String rightExample;
    private int leftCount;
    private int rightCount;
    private int leftRate;
    private int rightRate;
    private Map<String, Integer> optionCount;
}

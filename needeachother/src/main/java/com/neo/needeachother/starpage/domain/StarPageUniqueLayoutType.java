package com.neo.needeachother.starpage.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StarPageUniqueLayoutType {
    REPRESENTATIVE_ARTICLE(2, 3, "대표 게시글"),
    SCHEDULE(1, 5, "스케줄");

    private final int tapCount;
    private final int articleCountPerTap;
    private final String layoutTitle;
}
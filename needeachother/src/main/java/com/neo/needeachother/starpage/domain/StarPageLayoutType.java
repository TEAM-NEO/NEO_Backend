package com.neo.needeachother.starpage.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StarPageLayoutType {
    REPRESENTATIVE_ARTICLE(2, 3, "대표 게시글"),
    SCHEDULE(1, 5, "스케줄"),
    COMMON(1, 5, null),
    HORIZONTAL_SCROLL_ALBUM(1, 10, null),
    ALBUM(1, 10, null),
    OX(1, 1, null),
    VOTE(1, 1, null);

    private final int tapCount;
    private final int articleCountPerTap;
    private final String layoutTitle;
}
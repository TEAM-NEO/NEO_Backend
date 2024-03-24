package com.neo.needeachother.starpage.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoricalLayoutType {
    COMMON(5),
    HORIZONTAL_SCROLL_ALBUM(10),
    ALBUM(10),
    OX(1),
    VOTE(1);

    private final int articleCount;
}

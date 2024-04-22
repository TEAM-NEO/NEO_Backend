package com.neo.needeachother.starpage.domain;

import com.neo.needeachother.category.domain.ContentType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum StarPageLayoutType {
    REPRESENTATIVE_ARTICLE(2, 3, "대표 게시글", null),
    SCHEDULE(1, 5, "스케줄", null),
    COMMON(1, 5, null, ContentType.COMMON),
    HORIZONTAL_SCROLL_ALBUM(1, 10, null, ContentType.ALBUM),
    ALBUM(1, 5, null, ContentType.ALBUM),
    GOLD_BALANCE(1, 1, null, ContentType.GOLD_BALANCE),
    VOTE(1, 1, null, ContentType.VOTE);

    private final int tapCount;
    private final int articleCountPerTap;
    private final String layoutTitle;
    private final ContentType contentType;

    public static StarPageLayoutType ofCategoryType(ContentType categoryContentType, boolean isHorizontalLayout){
        if (categoryContentType == ContentType.ALBUM){
            return isHorizontalLayout ? HORIZONTAL_SCROLL_ALBUM : ALBUM;
        }
        return Arrays.stream(StarPageLayoutType.values())
                .filter(starPageLayoutType -> starPageLayoutType.getContentType() == categoryContentType)
                .findAny()
                .orElseThrow();
    }
}
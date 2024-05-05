package com.neo.needeachother.starpage.domain.dto;

import com.neo.needeachother.starpage.application.dto.StarPageLayoutViewTileData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class StarPageHeadLine {
    private final Long postId;
    private final int likeCount;
    private final String categoryType;
}

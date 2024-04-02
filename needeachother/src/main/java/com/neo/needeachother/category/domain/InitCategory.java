package com.neo.needeachother.category.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InitCategory {
    NOTICE("공지사항", ContentType.COMMON),
    FREE_BOARD("자유게시판", ContentType.COMMON),
    SELFI("셆카", ContentType.ALBUM),
    FREE_ALBUM("일상사진", ContentType.ALBUM);

    private final String koreanTitle;
    private final ContentType contentType;
}

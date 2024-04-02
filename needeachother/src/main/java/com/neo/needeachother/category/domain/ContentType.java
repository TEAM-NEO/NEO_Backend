package com.neo.needeachother.category.domain;

import com.neo.needeachother.common.exception.NEOUnexpectedException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ContentType {
    COMMON("C", "CO"),
    ALBUM("A", "AL"),
    OX("OX", "OX"),
    VOTE("V", "VO");

    // 카테고리의 컨텐츠 타입 요약 심볼(DB 데이터)
    private final String contentTypeSummarizedSymbol;

    // 카테고리 앞 접두사
    private final String prefixCategoryId;

    public static ContentType convertToContentType(String dbSymbol){
        return Arrays.stream(ContentType.values())
                .filter(contentType -> contentType.getContentTypeSummarizedSymbol().equals(dbSymbol))
                .findAny()
                .orElseThrow(() -> new NEOUnexpectedException("경고 : 예상하지 못한 카테고리 컨텐츠 타입값이 저장됨"));
    }


}

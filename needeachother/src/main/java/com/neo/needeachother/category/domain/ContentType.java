package com.neo.needeachother.category.domain;

import com.neo.needeachother.common.exception.NEOUnexpectedException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ContentType {
    COMMON("C"),
    ALBUM("A"),
    OX("OX"),
    VOTE("V");

    private final String contentTypeSummarizedSymbol;

    public static ContentType convertToContentType(String dbSymbol){
        return Arrays.stream(ContentType.values())
                .filter(contentType -> contentType.getContentTypeSummarizedSymbol().equals(dbSymbol))
                .findAny()
                .orElseThrow(() -> new NEOUnexpectedException("경고 : 예상하지 못한 카테고리 컨텐츠 타입값이 저장됨"));
    }
}

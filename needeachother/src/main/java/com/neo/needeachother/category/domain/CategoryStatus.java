package com.neo.needeachother.category.domain;

import com.neo.needeachother.common.exception.NEOUnexpectedException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum CategoryStatus {
    OPEN("O"),
    EXPOSURE("E"),
    DELETED("D");

    private final String categorySummarizedSymbol;

    public static CategoryStatus convertToCategoryStatus(String dbSymbol){
        return Arrays.stream(CategoryStatus.values())
                .filter(categoryStatus -> categoryStatus.getCategorySummarizedSymbol().equals(dbSymbol))
                .findAny()
                .orElseThrow(() -> new NEOUnexpectedException("경고 : 정상적이지 않은 카테고리 상테 심볼이 DB에 저장"));
    }
}

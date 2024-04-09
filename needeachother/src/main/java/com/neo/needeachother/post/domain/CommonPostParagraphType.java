package com.neo.needeachother.post.domain;

import com.neo.needeachother.common.exception.NEOUnexpectedException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum CommonPostParagraphType {
    TEXT("T"),
    IMAGE("I");

    private final String commonPostParagraphTypeSummarizedSymbol;

    public static CommonPostParagraphType convertToCommonPostContentType(String dbSymbol){
        return Arrays.stream(CommonPostParagraphType.values())
                .filter(type -> type.getCommonPostParagraphTypeSummarizedSymbol().equals(dbSymbol))
                .findAny()
                .orElseThrow(() -> new NEOUnexpectedException("예상하지 못한 통합형 게시글의 단락 타입이 DB에 저장되었음."));
    }
}

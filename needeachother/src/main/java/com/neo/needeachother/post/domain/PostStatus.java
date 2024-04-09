package com.neo.needeachother.post.domain;

import com.neo.needeachother.common.exception.NEOUnexpectedException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum PostStatus {
    OPEN("OPEN"),
    MAIN_EXPOSED("POP"),
    DELETED("DEL");

    private final String postStatusSummarizedSymbol;

    public static PostStatus convertToPostStatus(String dbSymbol){
        return Arrays.stream(PostStatus.values())
                .filter(postStatus -> postStatus.getPostStatusSummarizedSymbol().equals(dbSymbol))
                .findAny()
                .orElseThrow(() -> new NEOUnexpectedException("정상적이지 않은 포스트 상태값이 DB에 삽입되어있습니다."));
    }
}

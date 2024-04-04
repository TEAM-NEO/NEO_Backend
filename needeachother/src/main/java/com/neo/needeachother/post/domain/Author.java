package com.neo.needeachother.post.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Author {

    private String authorName;

    // 멤버 도메인 개편 이후 멤버 아이디 삽입.

    public static Author of(String authorName){
        return new Author(authorName);
    }
}

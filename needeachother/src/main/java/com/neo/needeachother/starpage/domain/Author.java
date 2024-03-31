package com.neo.needeachother.starpage.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Author {
    private final String nickName;

    public String getNickName() {
        return nickName;
    }
}

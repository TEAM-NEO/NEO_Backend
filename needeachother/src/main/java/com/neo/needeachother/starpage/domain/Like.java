package com.neo.needeachother.starpage.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Like {
    private final int count;

    public int getCount() {
        return count;
    }

}

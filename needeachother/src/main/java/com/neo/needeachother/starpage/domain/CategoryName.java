package com.neo.needeachother.starpage.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryName {
    private final String value;

    public String getValue(){
        return this.value;
    }
}

package com.neo.needeachother.starpage.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StarPageIntroduction {
    private final String value;

    public String getIntroduction(){
        return this.value;
    }

    public static StarPageIntroduction of(String value){
        return new StarPageIntroduction(value);
    }
}

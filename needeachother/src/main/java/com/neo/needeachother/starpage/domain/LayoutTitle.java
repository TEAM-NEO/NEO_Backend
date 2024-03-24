package com.neo.needeachother.starpage.domain;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LayoutTitle {
    private String value;

    public static LayoutTitle of(String value){
        return new LayoutTitle(value);
    }
}

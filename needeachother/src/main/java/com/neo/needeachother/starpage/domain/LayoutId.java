package com.neo.needeachother.starpage.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LayoutId {
    private long value;

    public static LayoutId of(long value){
        return new LayoutId(value);
    }
}

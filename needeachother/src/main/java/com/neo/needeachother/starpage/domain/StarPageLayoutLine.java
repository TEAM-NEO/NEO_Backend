package com.neo.needeachother.starpage.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class StarPageLayoutLine {

    private LayoutId layoutId;
    private LayoutTitle layoutTitle;

    public StarPageLayoutLine(LayoutTitle layoutTitle){
        this.layoutTitle = layoutTitle;
    }


    public abstract boolean isRemoveAble();
}

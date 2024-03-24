package com.neo.needeachother.starpage.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoricalLayoutLine extends StarPageLayoutLine{
    private CategoryId categoryId;
    private CategoricalLayoutType layoutType;

    public CategoricalLayoutLine(LayoutTitle layoutTitle,
                                 CategoryId categoryId, CategoricalLayoutType layoutType){
        super(layoutTitle);
        this.categoryId = categoryId;
        this.layoutType = layoutType;
    }

    @Override
    public boolean isRemoveAble() {
        return true;
    }
}

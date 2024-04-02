package com.neo.needeachother.starpage.domain;

import com.neo.needeachother.category.domain.CategoryId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@DiscriminatorValue("CL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoricalLayoutLine extends StarPageLayoutLine{

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "category_id"))
    private CategoryId categoryId;

    public CategoricalLayoutLine(LayoutTitle layoutTitle,
                                 CategoryId categoryId, StarPageLayoutType layoutType){
        super(layoutTitle, layoutType);
        this.categoryId = categoryId;
    }

    @Override
    public boolean isRemoveAble() {
        return true;
    }
}

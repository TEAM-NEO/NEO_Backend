package com.neo.needeachother.starpage.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@DiscriminatorValue("SUL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StarPageUniqueLayoutLine extends StarPageLayoutLine {

    public StarPageUniqueLayoutLine(StarPageLayoutType type) {
        super(LayoutTitle.of(type.getLayoutTitle()), type);
    }

    @Override
    public boolean isRemoveAble() {
        return false;
    }

    public static StarPageUniqueLayoutLine representativeArticleLayoutLine() {
        return new StarPageUniqueLayoutLine(StarPageLayoutType.REPRESENTATIVE_ARTICLE);
    }

    public static StarPageUniqueLayoutLine scheduleLayoutLine() {
        return new StarPageUniqueLayoutLine(StarPageLayoutType.SCHEDULE);
    }
}

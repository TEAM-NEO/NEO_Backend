package com.neo.needeachother.starpage.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StarPageUniqueLayoutLine extends StarPageLayoutLine {
    private StarPageUniqueLayoutType type;

    public StarPageUniqueLayoutLine(LayoutTitle layoutTitle, StarPageUniqueLayoutType type) {
        super(layoutTitle);
        this.type = type;
    }

    @Override
    public boolean isRemoveAble() {
        return false;
    }

    public static StarPageUniqueLayoutLine representativeArticleLayoutLine() {
        return new StarPageUniqueLayoutLine(
                LayoutTitle.of(StarPageUniqueLayoutType.REPRESENTATIVE_ARTICLE.getLayoutTitle()),
                StarPageUniqueLayoutType.REPRESENTATIVE_ARTICLE);
    }

    public static StarPageUniqueLayoutLine scheduleLayoutLine() {
        return new StarPageUniqueLayoutLine(
                LayoutTitle.of(StarPageUniqueLayoutType.SCHEDULE.getLayoutTitle()),
                StarPageUniqueLayoutType.SCHEDULE);
    }
}

package com.neo.needeachother.starpage.domain;

import com.neo.needeachother.common.exception.NEOUnexpectedException;
import com.neo.needeachother.starpage.domain.dto.RepresentativeArticleHeadLine;
import com.neo.needeachother.starpage.domain.dto.StarPageHeadLine;
import com.neo.needeachother.starpage.domain.repository.StarPageRepository;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

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

    @Override
    public List<? extends StarPageHeadLine> getHeadLineByLayout(StarPageId starPageId, StarPageRepository repo){
        switch (this.getType()){
            case REPRESENTATIVE_ARTICLE -> {
                return this.getRepresentativeArticleHeadline(starPageId, repo);
            }
            default -> throw new NEOUnexpectedException("");
        }
    }

    private List<RepresentativeArticleHeadLine> getRepresentativeArticleHeadline(StarPageId starPageId, StarPageRepository repo){
        return Stream.concat(repo.searchHeadLineByStarPageIdAndLimit(starPageId, this.getType().getArticleCountPerTap()).stream(),
                repo.searchRecentHostHeadLineByStarPageIdAndLimit(starPageId, this.getType().getArticleCountPerTap()).stream())
                .toList();
    }

    public static StarPageUniqueLayoutLine representativeArticleLayoutLine() {
        return new StarPageUniqueLayoutLine(StarPageLayoutType.REPRESENTATIVE_ARTICLE);
    }

    public static StarPageUniqueLayoutLine scheduleLayoutLine() {
        return new StarPageUniqueLayoutLine(StarPageLayoutType.SCHEDULE);
    }
}

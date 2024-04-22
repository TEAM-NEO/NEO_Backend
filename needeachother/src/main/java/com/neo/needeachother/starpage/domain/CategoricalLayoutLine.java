package com.neo.needeachother.starpage.domain;

import com.neo.needeachother.category.domain.CategoryId;
import com.neo.needeachother.common.exception.NEOUnexpectedException;
import com.neo.needeachother.starpage.domain.dto.*;
import com.neo.needeachother.starpage.domain.repository.StarPageRepository;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @Override
    public List<? extends StarPageHeadLine> getHeadLineByLayout(StarPageId starPageId, StarPageRepository repo){
        switch (this.getType()){
            case COMMON -> {
                return this.getCommonHeadLineByLayout(starPageId, repo);
            }
            case VOTE -> {
                return this.getVoteHeadLineByLayout(starPageId, repo);
            }
            case ALBUM -> {
                return this.getAlbumHeadLineByLayout(starPageId, repo);
            }
            case GOLD_BALANCE -> {
                return this.getGoldBalanceHeadLineByLayout(starPageId, repo);
            }
            default -> throw new NEOUnexpectedException("");
        }
    }

    public List<CommonPostHeadLine> getCommonHeadLineByLayout(StarPageId starpageId, StarPageRepository repo){
        return repo.searchCommonPostHeadLineByCategoryId(starpageId, this.categoryId, this.getType().getArticleCountPerTap());
    }

    public List<ImagePostHeadLine> getAlbumHeadLineByLayout(StarPageId starpageId, StarPageRepository repo){
        return repo.searchImagePostHeadLineByCategoryId(starpageId, this.categoryId, this.getType().getArticleCountPerTap());
    }

    public List<GoldBalancePostHeadLine> getGoldBalanceHeadLineByLayout(StarPageId starpageId, StarPageRepository repo){
        return repo.searchGoldBalancePostHeadLineByCategoryId(starpageId, this.categoryId, this.getType().getArticleCountPerTap());
    }

    public List<VotePostHeadLine> getVoteHeadLineByLayout(StarPageId starpageId, StarPageRepository repo){
        return repo.searchVotePostHeadLineByCategoryId(starpageId, this.categoryId, this.getType().getArticleCountPerTap());
    }
}

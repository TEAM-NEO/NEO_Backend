package com.neo.needeachother.starpage.domain.repository;

import com.neo.needeachother.category.domain.CategoryId;
import com.neo.needeachother.starpage.domain.StarPage;
import com.neo.needeachother.starpage.domain.StarPageId;
import com.neo.needeachother.starpage.domain.dto.*;

import java.util.List;
import java.util.Optional;

public interface StarPageRepositoryCustom {
    List<RepresentativeArticleHeadLine> searchHeadLineByStarPageIdAndLimit(StarPageId id, long limit);
    List<RepresentativeArticleHeadLine> searchRecentHostHeadLineByStarPageIdAndLimit(StarPageId id, long limit);
    List<CommonPostHeadLine> searchCommonPostHeadLineByCategoryId(StarPageId starPageId, CategoryId categoryId, long limit);
    List<ImagePostHeadLine> searchImagePostHeadLineByCategoryId(StarPageId starPageId, CategoryId categoryId, long limit);
    List<GoldBalancePostHeadLine> searchGoldBalancePostHeadLineByCategoryId(StarPageId starPageId, CategoryId categoryId, long limit);
    List<VotePostHeadLine> searchVotePostHeadLineByCategoryId(StarPageId starPageId, CategoryId categoryId, long limit);
    Optional<StarPage> findStarPageWithLayout(StarPageId starPageId);
    Optional<StarPage> findStarPageWithInformation(StarPageId starPageId);
}

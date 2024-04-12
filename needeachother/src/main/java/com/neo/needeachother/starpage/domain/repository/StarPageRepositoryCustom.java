package com.neo.needeachother.starpage.domain.repository;

import com.neo.needeachother.category.domain.CategoryId;
import com.neo.needeachother.starpage.domain.StarPageId;
import com.neo.needeachother.starpage.domain.dto.HeadLine;

import java.util.List;

public interface StarPageRepositoryCustom {
    List<HeadLine> searchHeadLineByStarPageIdAndLimit(StarPageId id, long limit);
}

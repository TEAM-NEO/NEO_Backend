package com.neo.needeachother.category.domain.repository;

import com.neo.needeachother.category.domain.CategoryId;
import com.neo.needeachother.category.domain.ContentType;
import com.neo.needeachother.category.domain.dto.CategoryDetailViewDto;
import com.neo.needeachother.category.domain.dto.CategoryViewDto;
import com.neo.needeachother.starpage.domain.StarPageId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepositoryCustom {
    Optional<CategoryViewDto> searchCategoryViewByStarPageId(StarPageId starPageId);
    Optional<CategoryDetailViewDto> searchCategoryDetailViewByStarPageId(StarPageId starPageId);
}

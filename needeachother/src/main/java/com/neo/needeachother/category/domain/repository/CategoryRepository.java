package com.neo.needeachother.category.domain.repository;

import com.neo.needeachother.category.domain.Category;
import com.neo.needeachother.category.domain.CategoryId;
import com.neo.needeachother.starpage.domain.StarPageId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends CategoryRepositoryCustom, JpaRepository<Category, CategoryId> {
    Optional<Category> findByCategoryIdAndStarPageId(CategoryId categoryId, StarPageId starPageId);
}


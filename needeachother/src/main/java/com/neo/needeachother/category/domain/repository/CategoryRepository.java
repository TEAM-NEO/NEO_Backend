package com.neo.needeachother.category.domain.repository;

import com.neo.needeachother.category.domain.Category;
import com.neo.needeachother.category.domain.CategoryId;
import com.neo.needeachother.category.domain.ContentType;
import com.neo.needeachother.starpage.domain.StarPageId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends CategoryCustomRepository, JpaRepository<Category, CategoryId> {
    Optional<Category> findByCategoryIdAndStarPageId(CategoryId categoryId, StarPageId starPageId);
}


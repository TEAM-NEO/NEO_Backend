package com.neo.needeachother.category.application;

import com.neo.needeachother.category.domain.Category;
import com.neo.needeachother.category.domain.CategoryId;
import com.neo.needeachother.category.domain.repository.CategoryRepository;
import com.neo.needeachother.starpage.domain.StarPageId;

public final class CategoryServiceHelper {
    public static Category findExistingCategory(CategoryRepository repo, CategoryId categoryId){
        return repo.findById(categoryId)
                .orElseThrow();
    }

    public static Category findExistingCategoryByStarPageId(CategoryRepository repo, CategoryId categoryId, StarPageId starPageId){
        return repo.findByCategoryIdAndStarPageId(categoryId, starPageId)
                .orElseThrow();
    }
}

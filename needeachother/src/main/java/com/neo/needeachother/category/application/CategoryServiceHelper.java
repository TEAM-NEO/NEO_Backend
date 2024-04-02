package com.neo.needeachother.category.application;

import com.neo.needeachother.category.domain.Category;
import com.neo.needeachother.category.domain.CategoryId;
import com.neo.needeachother.category.domain.repository.CategoryRepository;

public final class CategoryServiceHelper {
    public static Category findExistingCategory(CategoryRepository repo, CategoryId categoryId){
        return repo.findById(categoryId)
                .orElseThrow();
    }
}

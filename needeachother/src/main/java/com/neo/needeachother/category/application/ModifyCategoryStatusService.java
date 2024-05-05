package com.neo.needeachother.category.application;

import com.neo.needeachother.category.domain.Category;
import com.neo.needeachother.category.domain.CategoryId;
import com.neo.needeachother.category.domain.domainservice.ConfirmCategoryChangeableAdminService;
import com.neo.needeachother.category.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.neo.needeachother.category.application.CategoryServiceHelper.*;

@Service
@RequiredArgsConstructor
public class ModifyCategoryStatusService {

    private final CategoryRepository categoryRepository;
    private final ConfirmCategoryChangeableAdminService confirmCategoryChangeableAdminService;

    @Transactional
    public void reOpenCategory(String categoryId, String email){
        Category foundCategory = findExistingCategory(categoryRepository, CategoryId.of(categoryId));
        foundCategory.reOpenCategory(confirmCategoryChangeableAdminService, email);
    }

    @Transactional
    public void deleteCategory(String categoryId, String email){
        Category foundCategory = findExistingCategory(categoryRepository, CategoryId.of(categoryId));
        foundCategory.deleteCategory(confirmCategoryChangeableAdminService, email);
    }

    @Transactional
    public void exposureCategory(CategoryId id, String email){
        Category foundCategory = findExistingCategory(categoryRepository, id);
        foundCategory.exposureCategory(confirmCategoryChangeableAdminService, email);
    }

}

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
    public void reOpenCategory(CategoryId id, String email){
        Category foundCategory = findExistingCategory(categoryRepository, id);
        foundCategory.reOpenCategory(confirmCategoryChangeableAdminService, email);
    }

    @Transactional
    public void deleteCategory(CategoryId id, String email){
        Category foundCategory = findExistingCategory(categoryRepository, id);
        foundCategory.deleteCategory(confirmCategoryChangeableAdminService, email);
    }

}

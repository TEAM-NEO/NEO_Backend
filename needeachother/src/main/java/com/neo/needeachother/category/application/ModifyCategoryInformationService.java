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
public class ModifyCategoryInformationService {

    private final CategoryRepository categoryRepository;
    private final ConfirmCategoryChangeableAdminService confirmCategoryChangeableAdminService;

    @Transactional
    public void changeCategoryTitle(CategoryId categoryId, String email, String title){
        Category foundCategory = findExistingCategory(categoryRepository, categoryId);
        foundCategory.modifyCategoryTitle(confirmCategoryChangeableAdminService, email, title);
    }
}

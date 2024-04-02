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
public class CategoryVoteFilterService {

    private final CategoryRepository categoryRepository;
    private final ConfirmCategoryChangeableAdminService confirmCategoryChangeableAdminService;

    @Transactional
    public void modifyFilteringRate(CategoryId categoryId, String email, int changingRate){
        Category foundCategory = findExistingCategory(categoryRepository, categoryId);
        foundCategory.modifyFilteringRate(confirmCategoryChangeableAdminService, email, changingRate);
    }

    @Transactional
    public void useFilter(CategoryId categoryId, String email){
        Category foundCategory = findExistingCategory(categoryRepository, categoryId);
        foundCategory.turnOnCommentRatingFilter(confirmCategoryChangeableAdminService, email);
    }

    @Transactional
    public void notUseFilter(CategoryId categoryId, String email){
        Category foundCategory = findExistingCategory(categoryRepository, categoryId);
        foundCategory.turnOffCommentRatingFilter(confirmCategoryChangeableAdminService, email);
    }
}

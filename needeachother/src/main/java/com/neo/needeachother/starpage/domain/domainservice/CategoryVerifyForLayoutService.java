package com.neo.needeachother.starpage.domain.domainservice;

import com.neo.needeachother.category.domain.Category;
import com.neo.needeachother.category.domain.CategoryId;
import com.neo.needeachother.category.domain.repository.CategoryRepository;
import com.neo.needeachother.starpage.domain.StarPageId;
import com.neo.needeachother.starpage.domain.StarPageLayoutType;
import com.neo.needeachother.starpage.domain.dto.VerifiedCategoricalLayoutInformation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.neo.needeachother.category.application.CategoryServiceHelper.*;

@Service
@RequiredArgsConstructor
public class CategoryVerifyForLayoutService {

    private final CategoryRepository categoryRepository;

    public VerifiedCategoricalLayoutInformation verifyCategoryAndGetInformationForLayout(StarPageId starPageId, CategoryId categoryId, boolean isHorizontalLayout) {
        Category foundCategory = findExistingCategoryByStarPageId(categoryRepository, categoryId, starPageId);
        return VerifiedCategoricalLayoutInformation.builder()
                .categoryId(foundCategory.getCategoryId())
                .categoryTitle(foundCategory.getCategoryInformation().getCategoryTitle())
                .starPageLayoutType(StarPageLayoutType.ofCategoryType(foundCategory.getContentType(), isHorizontalLayout))
                .build();
    }

}

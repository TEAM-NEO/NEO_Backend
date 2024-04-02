package com.neo.needeachother.category.application;

import com.neo.needeachother.category.domain.Category;
import com.neo.needeachother.category.domain.ContentType;
import com.neo.needeachother.category.domain.InitCategory;
import com.neo.needeachother.category.domain.repository.CategoryRepository;
import com.neo.needeachother.starpage.domain.StarPageId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class InitialCategoryCreationService {

    private final CategoryRepository categoryRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createInitialCategory(StarPageId starPageId) {
        List<Category> createdInitialCategory = Category.getInitialCategoryByStarPageId(
                Arrays.stream(InitCategory.values())
                        .map(InitCategory::getContentType)
                        .map(categoryRepository::getNextId)
                        .collect(Collectors.toList()),
                starPageId);

        categoryRepository.saveAll(createdInitialCategory);
    }

}

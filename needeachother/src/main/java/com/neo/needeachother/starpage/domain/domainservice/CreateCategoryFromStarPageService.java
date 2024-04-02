package com.neo.needeachother.starpage.domain.domainservice;

import com.neo.needeachother.category.domain.CategoryId;
import com.neo.needeachother.category.domain.ContentType;
import com.neo.needeachother.category.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCategoryFromStarPageService {

    private final CategoryRepository categoryRepository;

    public CategoryId createCategoryId(ContentType contentType){
        return categoryRepository.getNextId(contentType);
    }
}

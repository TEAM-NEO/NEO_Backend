package com.neo.needeachother.category.representation;

import com.neo.needeachother.category.application.CreateCategoryService;
import com.neo.needeachother.category.representation.dto.CategoryCreationRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryCreationController {

    private final CreateCategoryService createCategoryService;

    @PostMapping()
    public void demandCreateCategory(@Valid @RequestBody CategoryCreationRequest categoryCreationRequest) {
        createCategoryService.createCategory(
                categoryCreationRequest.getStarPageId(),
                categoryCreationRequest.getCategoryTitle(),
                categoryCreationRequest.getCategoryType(),
                categoryCreationRequest.isWriteHostOnly(),
                categoryCreationRequest.isCommentWriteAble(),
                categoryCreationRequest.isUsingRatingFilter(),
                categoryCreationRequest.getFilteringRate()
        );
    }
}

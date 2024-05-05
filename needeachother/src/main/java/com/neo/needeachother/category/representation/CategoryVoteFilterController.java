package com.neo.needeachother.category.representation;

import com.neo.needeachother.category.application.CategoryVoteFilterService;
import com.neo.needeachother.category.representation.dto.CategoryVoteFilterRateChangeRequest;
import com.neo.needeachother.category.representation.dto.CategoryVoteFilterStatusChangeRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryVoteFilterController {

    private final CategoryVoteFilterService categoryVoteFilterService;

    @PutMapping("/{category_id}/filter")
    public void demandUseOrNotFilter(
            @PathVariable("category_id") String categoryId,
            @Valid @RequestBody CategoryVoteFilterStatusChangeRequest voteFilterStatusChangeRequest,
            @RequestParam("use") boolean useFilter
    ) {
        if (useFilter) {
            categoryVoteFilterService.useFilter(categoryId, voteFilterStatusChangeRequest.getEmail());
        } else {
            categoryVoteFilterService.notUseFilter(categoryId, voteFilterStatusChangeRequest.getEmail());
        }

    }

    @PutMapping("/{category_id}/filter/rate")
    public void demandChangeFilteringRate(
            @PathVariable("category_id") String categoryId,
            @Valid @RequestBody CategoryVoteFilterRateChangeRequest voteFilterRateChangeRequest) {
        categoryVoteFilterService.modifyFilteringRate(
                categoryId,
                voteFilterRateChangeRequest.getEmail(),
                voteFilterRateChangeRequest.getFilteringRate());
    }
}

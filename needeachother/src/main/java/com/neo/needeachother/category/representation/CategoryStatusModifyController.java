package com.neo.needeachother.category.representation;

import com.neo.needeachother.category.application.ModifyCategoryStatusService;
import com.neo.needeachother.category.representation.dto.CategoryStatusModifyRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryStatusModifyController {

    private final ModifyCategoryStatusService modifyCategoryStatusService;

    @DeleteMapping("/{category_id}")
    private void demandDeleteCategory(
            @PathVariable("category_id") String categoryId,
            @Valid @RequestBody CategoryStatusModifyRequest categoryStatusModifyRequest
    ) {
        modifyCategoryStatusService.deleteCategory(categoryId, categoryStatusModifyRequest.getEmail());
    }

    @PutMapping("/{category_id}")
    private void demandOpenCategory(
            @PathVariable("category_id") String categoryId,
            @Valid @RequestBody CategoryStatusModifyRequest categoryStatusModifyRequest) {
        modifyCategoryStatusService.reOpenCategory(categoryId, categoryStatusModifyRequest.getEmail());
    }
}

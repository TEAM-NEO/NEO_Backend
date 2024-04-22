package com.neo.needeachother.starpage.domain.dto;

import com.neo.needeachother.category.domain.CategoryId;
import com.neo.needeachother.starpage.domain.StarPageLayoutType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class VerifiedCategoricalLayoutInformation {
    private final CategoryId categoryId;
    private final String categoryTitle;
    private final StarPageLayoutType starPageLayoutType;
}

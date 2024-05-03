package com.neo.needeachother.category.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class CategoryDetailViewDto {
    private String starPageId;
    private List<CategoryDetailViewData> viewData;
}

package com.neo.needeachother.category.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class CategoryViewDto {
    private String starPageId;
    private List<CategoryViewData> viewData;
}

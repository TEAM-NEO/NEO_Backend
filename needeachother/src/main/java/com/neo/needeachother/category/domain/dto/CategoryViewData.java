package com.neo.needeachother.category.domain.dto;

import com.neo.needeachother.category.domain.ContentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class CategoryViewData {
    private String categoryId;
    private String categoryTitle;
    private String categoryType;
    private long postCountInCategory;

    public CategoryViewData(String categoryId, String categoryTitle, String categoryType, long postCountInCategory){
        this.categoryId = categoryId;
        this.categoryTitle = categoryTitle;
        this.categoryType = ContentType.convertToContentType(categoryType).name();
        this.postCountInCategory = postCountInCategory;
    }
}

package com.neo.needeachother.category.domain.dto;

import com.neo.needeachother.category.domain.ContentType;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class CategoryDetailViewData {
    private final String categoryId;
    private final String categoryTitle;
    private final String categoryType;
    private final boolean isHostWriteOnly;
    private final boolean isCommentWriteAble;
    private final boolean isUsingRateFilter;
    private final int voteFilterRate;
    private final long postInCategoryCount;

    public CategoryDetailViewData(String categoryId, String categoryTitle, String categoryType,
                                  boolean isHostWriteOnly, boolean isCommentWriteAble,
                                  boolean isUsingRateFilter,
                                  int voteFilterRate, long postInCategoryCount) {
        this.categoryId = categoryId;
        this.categoryTitle = categoryTitle;
        this.categoryType = ContentType.convertToContentType(categoryType).name();
        this.isHostWriteOnly = isHostWriteOnly;
        this.isCommentWriteAble = isCommentWriteAble;
        this.isUsingRateFilter = isUsingRateFilter;
        this.voteFilterRate = voteFilterRate;
        this.postInCategoryCount = postInCategoryCount;
    }
}

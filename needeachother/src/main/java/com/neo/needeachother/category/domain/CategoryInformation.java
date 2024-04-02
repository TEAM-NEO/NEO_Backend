package com.neo.needeachother.category.domain;

import com.neo.needeachother.common.exception.NEOUnexpectedException;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryInformation {

    private String categoryTitle;

    // 도메인 : 카테고리의 제목을 변경할 수 있다.
    protected CategoryInformation changeCategoryTitle(String categoryTitle){
        return new CategoryInformation(categoryTitle);
    }

    public static CategoryInformation of(String title){
        return new CategoryInformation(title);
    }
}

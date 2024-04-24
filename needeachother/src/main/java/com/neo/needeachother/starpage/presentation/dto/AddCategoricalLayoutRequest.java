package com.neo.needeachother.starpage.presentation.dto;

import com.neo.needeachother.category.domain.CategoryId;
import com.neo.needeachother.starpage.domain.StarPageId;
import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddCategoricalLayoutRequest {
    private String starPageId;
    private String email;
    private String categoryId;
}

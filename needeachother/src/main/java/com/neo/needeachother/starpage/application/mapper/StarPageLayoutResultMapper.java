package com.neo.needeachother.starpage.application.mapper;

import com.neo.needeachother.starpage.application.dto.StarPageLayoutResult;
import com.neo.needeachother.starpage.domain.CategoricalLayoutLine;
import com.neo.needeachother.starpage.domain.StarPageLayoutLine;
import org.springframework.stereotype.Component;

@Component
public class StarPageLayoutResultMapper implements Mapper<StarPageLayoutLine, StarPageLayoutResult> {

    @Override
    public StarPageLayoutResult map(StarPageLayoutLine input) {
        StarPageLayoutResult.StarPageLayoutResultBuilder builder = StarPageLayoutResult.builder()
                .layoutId(input.getLayoutId())
                .layoutTitle(input.getLayoutTitle().getValue())
                .layoutType(input.getType().name());

        if (input instanceof CategoricalLayoutLine) {
            builder.categoryId(((CategoricalLayoutLine) input).getCategoryId().getValue());
        }
        return builder.build();
    }
}

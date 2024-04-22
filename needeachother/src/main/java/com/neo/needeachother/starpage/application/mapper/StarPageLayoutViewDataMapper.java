package com.neo.needeachother.starpage.application.mapper;

import com.neo.needeachother.starpage.application.dto.StarPageLayoutViewData;
import com.neo.needeachother.starpage.application.dto.StarPageLayoutViewTileData;
import com.neo.needeachother.starpage.domain.dto.LayoutHeadLine;
import com.neo.needeachother.starpage.domain.dto.RepresentativeArticleHeadLine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@RequiredArgsConstructor
public class StarPageLayoutViewDataMapper implements Mapper<LayoutHeadLine, StarPageLayoutViewData> {

    private final StarPageLayoutViewTileDataMapper starPageLayoutViewTileDataMapper;

    @Override
    public StarPageLayoutViewData map(LayoutHeadLine input) {
        boolean hasTap = input.getLayoutContents().get(0) instanceof RepresentativeArticleHeadLine;
        return StarPageLayoutViewData.builder()
                .layoutTitle(input.getLayoutTitle())
                .hasTap(hasTap)
                .layoutTileDataWithTap(getStarPageLayoutViewTileDataMap(hasTap, input))
                .build();
    }

    private Map<String, List<StarPageLayoutViewTileData>> getStarPageLayoutViewTileDataMap(boolean hasTap, LayoutHeadLine layoutHeadLine) {
        Map<String, List<StarPageLayoutViewTileData>> tileMap = new HashMap<>();
        if (hasTap) {
            layoutHeadLine.getLayoutContents()
                    .stream()
                    .map(starPageHeadLine -> (RepresentativeArticleHeadLine) starPageHeadLine)
                    .forEach(representativeArticleHeadLine -> {
                        List<StarPageLayoutViewTileData> dataList = tileMap.getOrDefault(representativeArticleHeadLine.getTapName(), new ArrayList<>());
                        dataList.add(starPageLayoutViewTileDataMapper.map(representativeArticleHeadLine));
                        tileMap.put(representativeArticleHeadLine.getTapName(), dataList);
                    });
        } else {
            tileMap.put(layoutHeadLine.getLayoutTitle(), layoutHeadLine.getLayoutContents()
                    .stream()
                    .map(starPageLayoutViewTileDataMapper::map)
                    .toList());
        }
        return tileMap;
    }

}

package com.neo.needeachother.starpage.application.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@Builder
public class StarPageLayoutViewData {
    private String layoutTitle;
    private boolean hasTap;
    private Map<String, List<StarPageLayoutViewTileData>> layoutTileDataWithTap;
}

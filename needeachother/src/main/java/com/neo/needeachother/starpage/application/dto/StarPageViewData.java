package com.neo.needeachother.starpage.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@Builder
public class StarPageViewData {
    private String starPageId;
    private List<StarPageLayoutViewData> layoutViewData;
}

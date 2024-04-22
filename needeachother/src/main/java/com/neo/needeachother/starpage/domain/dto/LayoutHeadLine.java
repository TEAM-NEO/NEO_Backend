package com.neo.needeachother.starpage.domain.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class LayoutHeadLine {
    private final String layoutTitle;
    private final List<? extends StarPageHeadLine> layoutContents;
}

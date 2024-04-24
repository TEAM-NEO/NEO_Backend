package com.neo.needeachother.starpage.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@Builder
public class ModifiedStarTypeResult {
    private String starPageId;
    private List<String> modifiedStarTypes;
}

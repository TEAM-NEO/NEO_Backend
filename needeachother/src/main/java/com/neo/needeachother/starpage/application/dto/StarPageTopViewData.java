package com.neo.needeachother.starpage.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@Builder
public class StarPageTopViewData {
    private String starPageId;
    private String hostActiveName;
    private String starPageIntroduce;
    private List<String> starTypes;
    private Map<String, String> starUrl;
    private String topRepresentativeImageUrl;
    private String profileImageUrl;
}

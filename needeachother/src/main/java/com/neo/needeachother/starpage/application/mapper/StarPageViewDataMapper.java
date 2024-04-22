package com.neo.needeachother.starpage.application.mapper;

import com.neo.needeachother.starpage.application.dto.StarPageTopViewData;
import com.neo.needeachother.starpage.application.dto.StarPageViewData;
import com.neo.needeachother.starpage.domain.SNSLine;
import com.neo.needeachother.starpage.domain.StarPage;
import com.neo.needeachother.starpage.domain.StarPageId;
import com.neo.needeachother.starpage.domain.dto.LayoutHeadLine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StarPageViewDataMapper {

    private final StarPageLayoutViewDataMapper starPageLayoutViewDataMapper;

    public StarPageViewData mapToViewData(StarPageId starPageId, List<LayoutHeadLine> layoutHeadLines) {
        return StarPageViewData.builder()
                .starPageId(starPageId.getValue()).layoutViewData(layoutHeadLines.stream()
                        .map(starPageLayoutViewDataMapper::map)
                        .toList())
                .build();

    }

    public StarPageTopViewData mapToTopViewData(StarPage starPage){
        return StarPageTopViewData.builder()
                .starPageId(starPage.getStarPageId().getValue())
                .hostActiveName(starPage.getInformation().getHost().getStarNickName())
                .starPageIntroduce(starPage.getInformation().getIntroduction().getIntroduction())
                .starTypes(starPage.getInformation().getHost().getStarTypes()
                        .stream()
                        .map(Enum::name)
                        .collect(Collectors.toList())
                )
                .starUrl(starPage.getInformation().getHost().getSnsLines()
                        .stream()
                        .collect(Collectors.toMap(snsLine -> snsLine.getType().name(), SNSLine::getUrl))
                )
                .profileImageUrl(starPage.getInformation().getCurrentProfileImage().getUrl())
                .topRepresentativeImageUrl(starPage.getInformation().getCurrentTopRepresentativeImage().getUrl())
                .build();
    }
}

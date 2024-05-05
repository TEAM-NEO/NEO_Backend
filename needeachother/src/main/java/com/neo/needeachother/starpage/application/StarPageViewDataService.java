package com.neo.needeachother.starpage.application;

import com.neo.needeachother.starpage.application.dto.StarPageTopViewData;
import com.neo.needeachother.starpage.application.dto.StarPageViewData;
import com.neo.needeachother.starpage.application.mapper.StarPageViewDataMapper;
import com.neo.needeachother.starpage.domain.StarPage;
import com.neo.needeachother.starpage.domain.StarPageId;
import com.neo.needeachother.starpage.domain.dto.LayoutHeadLine;
import com.neo.needeachother.starpage.domain.repository.StarPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.neo.needeachother.starpage.application.StarPageServiceHelper.*;

@Service
@RequiredArgsConstructor
public class StarPageViewDataService {

    private final StarPageRepository starPageRepository;
    private final StarPageViewDataMapper starPageViewDataMapper;

    public StarPageTopViewData getStarPageTopViewData(String starPageId){
        // 스타페이지 정보 포함된 스타페이지
        StarPage foundStarPage = findExistingStarPageWithTopViewInformation(starPageRepository, new StarPageId(starPageId));

        return starPageViewDataMapper.mapToTopViewData(foundStarPage);
    }

    public StarPageViewData getStarPageLayoutViewData(String starPageId){
        // 레이아웃이 조인된 스타페이지
        StarPage foundStarPage = findExistingStarPageWithLayout(starPageRepository, new StarPageId(starPageId));

        // 레이아웃 순서에 따른 순차적인 뷰를 구성하는 데이터
        List<LayoutHeadLine> starPageViewLayoutList = foundStarPage.getLayoutHeadLines(starPageRepository);

        return starPageViewDataMapper.mapToViewData(foundStarPage.getStarPageId(), starPageViewLayoutList);
    }
}

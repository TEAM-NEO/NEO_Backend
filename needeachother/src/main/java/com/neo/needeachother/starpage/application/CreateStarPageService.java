package com.neo.needeachother.starpage.application;

import com.neo.needeachother.starpage.application.dto.CreatedStarPageResult;
import com.neo.needeachother.starpage.domain.SNSLine;
import com.neo.needeachother.starpage.domain.StarPage;
import com.neo.needeachother.starpage.domain.domainservice.StarPageIdGenerateService;
import com.neo.needeachother.starpage.domain.event.StarPageCreatedEvent;
import com.neo.needeachother.starpage.domain.repository.StarPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CreateStarPageService {

    private final StarPageRepository starPageRepository;
    private final StarPageIdGenerateService idGenerateService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public CreatedStarPageResult createStarPage(String starNickName, String email, Set<String> starTypeSet,
                                                List<SNSLine> snsLines, String starPageIntroduce){
        // 스타페이지 생성
        StarPage createdStarPage = StarPage.create(idGenerateService.getNextId(), starNickName, email,
                starTypeSet, snsLines, starPageIntroduce);

        // 영속화
        starPageRepository.save(createdStarPage);

        // 생성 이벤트 발행 (기본 카테고리 자동 생성)
        eventPublisher.publishEvent(new StarPageCreatedEvent(createdStarPage.getStarPageId()));

        // 결과 값 생성
        return CreatedStarPageResult.builder()
                .createdStarPageId(createdStarPage.getStarPageId().getValue())
                .build();
    }
}

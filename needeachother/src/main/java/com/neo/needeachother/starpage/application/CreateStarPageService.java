package com.neo.needeachother.starpage.application;

import com.neo.needeachother.starpage.domain.SNSLine;
import com.neo.needeachother.starpage.domain.StarPage;
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
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void createStarPage(String starNickName, String email, Set<String> starTypeSet,
                               List<SNSLine> snsLines, String starPageIntroduce){
        StarPage createdStarPage = StarPage.create(starPageRepository.getNextId(), starNickName, email,
                starTypeSet, snsLines, starPageIntroduce, eventPublisher);
        starPageRepository.save(createdStarPage);
    }
}

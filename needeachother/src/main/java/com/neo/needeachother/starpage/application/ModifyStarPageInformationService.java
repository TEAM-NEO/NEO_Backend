package com.neo.needeachother.starpage.application;

import com.neo.needeachother.starpage.domain.*;
import com.neo.needeachother.starpage.domain.repository.StarPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.neo.needeachother.starpage.application.StarPageServiceHelper.*;

@Service
@RequiredArgsConstructor
public class ModifyStarPageInformationService {

    private final StarPageRepository starPageRepository;

    @Transactional
    public void modifyProfileImage(StarPageId id, String email, Image modifyingImage) {
        StarPage foundStarPage = findExistingStarPage(starPageRepository, id);
        foundStarPage.changeProfileImage(NEOMember.of(email), modifyingImage);
    }

    @Transactional
    public void modifyTopRepresentativeImage(StarPageId id, String email, Image modifyingImage){
        StarPage foundStarPage = findExistingStarPage(starPageRepository, id);
        foundStarPage.changeTopRepresentativeImage(NEOMember.of(email), modifyingImage);
    }

    @Transactional
    public void modifyStarNickName(StarPageId id, String email, String modifyingNickName){
        StarPage foundStarPage = findExistingStarPage(starPageRepository, id);
        foundStarPage.changeStarNickName(email, modifyingNickName);
    }

    @Transactional
    public void modifyStarIntroduction(StarPageId id, String email, String modifyingIntroduce){
        StarPage foundStarPage = findExistingStarPage(starPageRepository, id);
        foundStarPage.changeStarPageIntroduction(NEOMember.of(email), StarPageIntroduction.of(modifyingIntroduce));
    }
}

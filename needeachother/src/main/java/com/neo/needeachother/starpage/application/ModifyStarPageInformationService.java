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
    public void modifyProfileImage(String starPageId, String email, String modifyingImage) {
        StarPage foundStarPage = findExistingStarPage(starPageRepository, StarPageId.of(starPageId));
        foundStarPage.changeProfileImage(NEOMember.of(email), Image.of(modifyingImage));
    }

    @Transactional
    public void modifyTopRepresentativeImage(String starPageId, String email, String modifyingImage){
        StarPage foundStarPage = findExistingStarPage(starPageRepository, StarPageId.of(starPageId));
        foundStarPage.changeTopRepresentativeImage(NEOMember.of(email), Image.of(modifyingImage));
    }

    @Transactional
    public void modifyStarNickName(String starPageId, String email, String modifyingNickName){
        StarPage foundStarPage = findExistingStarPage(starPageRepository, StarPageId.of(starPageId));
        foundStarPage.changeStarNickName(email, modifyingNickName);
    }

    @Transactional
    public void modifyStarIntroduction(String starPageId, String email, String modifyingIntroduce){
        StarPage foundStarPage = findExistingStarPage(starPageRepository, StarPageId.of(starPageId));
        foundStarPage.changeStarPageIntroduction(NEOMember.of(email), StarPageIntroduction.of(modifyingIntroduce));
    }
}

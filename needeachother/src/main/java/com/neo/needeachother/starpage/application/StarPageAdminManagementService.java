package com.neo.needeachother.starpage.application;

import com.neo.needeachother.starpage.domain.NEOMember;
import com.neo.needeachother.starpage.domain.StarPage;
import com.neo.needeachother.starpage.domain.StarPageId;
import com.neo.needeachother.starpage.domain.repository.StarPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.neo.needeachother.starpage.application.StarPageServiceHelper.*;

@Service
@RequiredArgsConstructor
public class StarPageAdminManagementService {

    private final StarPageRepository starPageRepository;

    @Transactional
    public void appointAsStarPageAdmin(String starPageId, String requesterEmail, String beingAdminEmail) {
        StarPage foundStarPage = findExistingStarPage(starPageRepository, StarPageId.of(starPageId));
        // TODO : 멤버 확인 도메인서비스 필요
        foundStarPage.registerNewAdmin(requesterEmail, NEOMember.of(beingAdminEmail));
    }
}

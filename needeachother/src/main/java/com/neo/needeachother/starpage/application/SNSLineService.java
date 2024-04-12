package com.neo.needeachother.starpage.application;

import com.neo.needeachother.starpage.domain.SNSLine;
import com.neo.needeachother.starpage.domain.SNSType;
import com.neo.needeachother.starpage.domain.StarPage;
import com.neo.needeachother.starpage.domain.StarPageId;
import com.neo.needeachother.starpage.domain.repository.StarPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.neo.needeachother.starpage.application.StarPageServiceHelper.*;

@Service
@RequiredArgsConstructor
public class SNSLineService {

    private final StarPageRepository starPageRepository;

    @Transactional
    public void appendSNSLine(StarPageId id, String email, String snsName, String snsUrl){
        StarPage foundStarPage = findExistingStarPage(starPageRepository, id);
        // can trigger illegalStatementException in SNSType.valueOf
        foundStarPage.registerNewSNSLine(email, SNSLine.of(SNSType.valueOf(snsName), snsUrl));
    }

    @Transactional
    public void deleteSNSLine(StarPageId id, String email, String snsName, String snsUrl){
        StarPage foundStarPage = findExistingStarPage(starPageRepository, id);
        // can trigger illegalStatementException in SNSType.valueOf
        foundStarPage.removeSNSLine(email, SNSLine.of(SNSType.valueOf(snsName), snsUrl));
    }

}

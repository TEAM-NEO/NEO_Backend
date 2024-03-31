package com.neo.needeachother.starpage.application;

import com.neo.needeachother.starpage.domain.StarPage;
import com.neo.needeachother.starpage.domain.StarPageId;
import com.neo.needeachother.starpage.domain.StarType;
import com.neo.needeachother.starpage.domain.repository.StarPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.neo.needeachother.starpage.application.StarPageServiceHelper.*;

@Service
@RequiredArgsConstructor
public class StarTypeService {

    private final StarPageRepository starPageRepository;

    @Transactional
    public void addStarType(StarPageId id, String email, String starTypeName){
        StarPage foundStarPage = findExistingStarPage(starPageRepository, id);
        // can trigger illegalStatementException in StarType.valueOf
        foundStarPage.registerNewStarType(email, StarType.valueOf(starTypeName));
    }

    public void removeStarType(StarPageId id, String email, String starTypeName){
        StarPage foundStarPage = findExistingStarPage(starPageRepository, id);
        // can trigger illegalStatementException in StarType.valueOf
        foundStarPage.removeStarType(email, StarType.valueOf(starTypeName));
    }
}

package com.neo.needeachother.starpage.application;

import com.neo.needeachother.category.domain.CategoryId;
import com.neo.needeachother.starpage.domain.*;
import com.neo.needeachother.starpage.domain.domainservice.CategoryVerifyForLayoutService;
import com.neo.needeachother.starpage.domain.repository.StarPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.neo.needeachother.starpage.application.StarPageServiceHelper.*;

@Service
@RequiredArgsConstructor
public class LayoutManagementService {

    private final StarPageRepository starPageRepository;
    private final CategoryVerifyForLayoutService categoryVerifyForLayoutService;

    @Transactional
    public void appendLayoutInStarPage(StarPageId id, String email, CategoryId categoryId){
        StarPage foundStarPage = findExistingStarPage(starPageRepository, id);
        foundStarPage.appendCategoricalLayoutLine(NEOMember.of(email), categoryVerifyForLayoutService, categoryId, false);
    }
}

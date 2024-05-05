package com.neo.needeachother.starpage.application;

import com.neo.needeachother.category.domain.CategoryId;
import com.neo.needeachother.starpage.application.dto.StarPageLayoutResult;
import com.neo.needeachother.starpage.application.mapper.StarPageLayoutResultMapper;
import com.neo.needeachother.starpage.domain.*;
import com.neo.needeachother.starpage.domain.domainservice.CategoryVerifyForLayoutService;
import com.neo.needeachother.starpage.domain.repository.StarPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.neo.needeachother.starpage.application.StarPageServiceHelper.*;

@Service
@RequiredArgsConstructor
public class LayoutManagementService {

    private final StarPageRepository starPageRepository;
    private final CategoryVerifyForLayoutService categoryVerifyForLayoutService;
    private final StarPageLayoutResultMapper starPageLayoutResultMapper;

    @Transactional
    public List<StarPageLayoutResult> appendLayoutInStarPage(String starPageId, String email, String categoryId) {
        StarPage foundStarPage = findExistingStarPage(starPageRepository, StarPageId.of(starPageId));

        foundStarPage.appendCategoricalLayoutLine(NEOMember.of(email), categoryVerifyForLayoutService, CategoryId.of(categoryId), false);

        return foundStarPage.getLayoutLines().stream()
                .map(starPageLayoutResultMapper::map)
                .toList();

    }
}

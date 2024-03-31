package com.neo.needeachother.starpage.application;

import com.neo.needeachother.starpage.domain.*;
import com.neo.needeachother.starpage.domain.repository.StarPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import static com.neo.needeachother.starpage.application.StarPageServiceHelper.*;

@Service
@RequiredArgsConstructor
public class LayoutService {

    private final StarPageRepository starPageRepository;

    public void appendLayoutInStarPage(StarPageId id, String email, CategoryId categoryId){
        StarPage foundStarPage = findExistingStarPage(starPageRepository, id);
        // foundStarPage.appendCategoricalLayoutLine(NEOMember.of(email));
    }
}

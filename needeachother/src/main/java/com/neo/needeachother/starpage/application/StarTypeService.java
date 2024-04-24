package com.neo.needeachother.starpage.application;

import com.neo.needeachother.starpage.application.dto.ModifiedStarTypeResult;
import com.neo.needeachother.starpage.domain.StarPage;
import com.neo.needeachother.starpage.domain.StarPageId;
import com.neo.needeachother.starpage.domain.StarType;
import com.neo.needeachother.starpage.domain.repository.StarPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

import static com.neo.needeachother.starpage.application.StarPageServiceHelper.*;

@Service
@RequiredArgsConstructor
public class StarTypeService {

    private final StarPageRepository starPageRepository;

    @Transactional
    public ModifiedStarTypeResult addStarType(String starPageId, String email, String starTypeName) {
        StarPage foundStarPage = findExistingStarPage(starPageRepository, StarPageId.of(starPageId));

        // can trigger illegalStatementException in StarType.valueOf
        foundStarPage.registerNewStarType(email, StarType.valueOf(starTypeName));

        return ModifiedStarTypeResult.builder()
                .starPageId(starPageId)
                .modifiedStarTypes(foundStarPage.getInformation().getHost().getStarTypes()
                        .stream()
                        .map(Enum::name)
                        .collect(Collectors.toList())
                )
                .build();
    }

    @Transactional
    public ModifiedStarTypeResult removeStarType(String starPageId, String email, String starTypeName) {
        StarPage foundStarPage = findExistingStarPage(starPageRepository, StarPageId.of(starPageId));
        // can trigger illegalStatementException in StarType.valueOf
        foundStarPage.removeStarType(email, StarType.valueOf(starTypeName));

        return ModifiedStarTypeResult.builder()
                .starPageId(starPageId)
                .modifiedStarTypes(foundStarPage.getInformation().getHost().getStarTypes()
                        .stream()
                        .map(Enum::name)
                        .collect(Collectors.toList())
                )
                .build();
    }
}

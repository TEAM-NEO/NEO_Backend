package com.neo.needeachother.starpage.application;

import com.neo.needeachother.common.enums.NEODomainType;
import com.neo.needeachother.common.enums.NEOErrorCode;
import com.neo.needeachother.common.exception.NEOExpectedException;
import com.neo.needeachother.common.exception.NEOUnexpectedException;
import com.neo.needeachother.starpage.domain.StarPage;
import com.neo.needeachother.starpage.domain.StarPageId;
import com.neo.needeachother.starpage.domain.repository.StarPageRepository;

public final class StarPageServiceHelper {
    public static StarPage findExistingStarPage(StarPageRepository repo, StarPageId starPageId){
        return repo.findById(starPageId)
                .orElseThrow(() -> new NEOExpectedException(NEODomainType.STARPAGE,
                        NEOErrorCode.NOT_EXIST_STARPAGE,
                        NEOErrorCode.NOT_EXIST_STARPAGE.getErrorDescription()));
    }
}

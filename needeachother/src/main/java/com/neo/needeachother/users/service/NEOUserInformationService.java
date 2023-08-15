package com.neo.needeachother.users.service;

import com.neo.needeachother.users.request.NEOCreateStarInfoRequest;
import org.springframework.stereotype.Service;

public interface NEOUserInformationService {
    void handleCreateNewStarInformationOrder(NEOCreateStarInfoRequest createStarInfoRequest);
}

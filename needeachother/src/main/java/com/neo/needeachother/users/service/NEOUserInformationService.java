package com.neo.needeachother.users.service;

import com.neo.needeachother.common.response.NEOResponseBody;
import com.neo.needeachother.users.controller.NEOUserInformationController;
import com.neo.needeachother.users.request.NEOCreateFanInfoRequest;
import com.neo.needeachother.users.request.NEOCreateStarInfoRequest;
import org.springframework.http.ResponseEntity;

public interface NEOUserInformationService {
     ResponseEntity<NEOResponseBody> handleCreateNewStarInformationOrder(NEOCreateStarInfoRequest createStarInfoRequest, NEOUserInformationController.NEOUserOrder userOrder);
     void handleCreateNewFanInformationOrder(NEOCreateFanInfoRequest createFanInfoRequest, NEOUserInformationController.NEOUserOrder userOrder);
}

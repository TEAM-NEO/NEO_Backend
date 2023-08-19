package com.neo.needeachother.users.service;

import com.neo.needeachother.common.response.NEOResponseBody;
import com.neo.needeachother.users.controller.NEOUserInformationController;
import com.neo.needeachother.users.request.NEOCreateFanInfoRequest;
import com.neo.needeachother.users.request.NEOCreateStarInfoRequest;
import org.springframework.http.ResponseEntity;

public interface NEOUserInformationService {
     ResponseEntity<NEOResponseBody> doCreateNewStarInformationOrder(NEOCreateStarInfoRequest createStarInfoRequest, NEOUserInformationController.NEOUserOrder userOrder);
     ResponseEntity<NEOResponseBody> doCreateNewFanInformationOrder(NEOCreateFanInfoRequest createFanInfoRequest, NEOUserInformationController.NEOUserOrder userOrder);
     ResponseEntity<?> doGetUserInformationOrder(String userID, NEOUserInformationController.NEOUserOrder userOrder);
     ResponseEntity<?> doGetPublicUserInformationOrder(String userID, NEOUserInformationController.NEOUserOrder userOrder);
}

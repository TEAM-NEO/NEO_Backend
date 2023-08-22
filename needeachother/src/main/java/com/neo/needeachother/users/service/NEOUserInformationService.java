package com.neo.needeachother.users.service;

import com.neo.needeachother.common.response.NEOResponseBody;
import com.neo.needeachother.users.controller.NEOUserInformationController;
import com.neo.needeachother.users.dto.NEOChangeableInfoDto;
import com.neo.needeachother.users.dto.NEOFanInfoDto;
import com.neo.needeachother.users.dto.NEOStarInfoDto;
import org.springframework.http.ResponseEntity;

public interface NEOUserInformationService {
     ResponseEntity<NEOResponseBody> doCreateNewStarInformationOrder(NEOStarInfoDto createStarInfoRequest, NEOUserInformationController.NEOUserOrder userOrder);
     ResponseEntity<NEOResponseBody> doCreateNewFanInformationOrder(NEOFanInfoDto createFanInfoRequest, NEOUserInformationController.NEOUserOrder userOrder);
     ResponseEntity<?> doGetUserInformationOrder(String userID, NEOUserInformationController.NEOUserOrder userOrder);
     ResponseEntity<?> doGetPublicUserInformationOrder(String userID, NEOUserInformationController.NEOUserOrder userOrder);
     ResponseEntity<?> doChangePartialInformationOrder(String userID, NEOUserInformationController.NEOUserOrder userOrder, NEOChangeableInfoDto changeInfoDto);
}

package com.neo.needeachother.users.service;

import com.neo.needeachother.users.dto.NEOChangeableInfoDto;
import com.neo.needeachother.users.dto.NEOFanInfoDto;
import com.neo.needeachother.users.dto.NEOStarInfoDto;
import com.neo.needeachother.users.enums.NEOUserOrder;
import org.springframework.http.ResponseEntity;

public interface NEOUserInformationService {
     ResponseEntity doCreateNewStarInformationOrder(NEOStarInfoDto createStarInfoRequest, NEOUserOrder userOrder);
     ResponseEntity<?> doCreateNewFanInformationOrder(NEOFanInfoDto createFanInfoRequest, NEOUserOrder userOrder);
     ResponseEntity<?> doGetUserInformationOrder(String userID, NEOUserOrder userOrder);
     ResponseEntity<?> doGetPublicUserInformationOrder(String userID, NEOUserOrder userOrder);
     ResponseEntity<?> doChangePartialInformationOrder(String userID, NEOUserOrder userOrder, NEOChangeableInfoDto changeInfoDto);
     ResponseEntity<?> doDeleteUserInformationOrder(String userID, NEOUserOrder userOrder);
}

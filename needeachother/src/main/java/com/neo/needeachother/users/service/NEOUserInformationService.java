package com.neo.needeachother.users.service;

import com.neo.needeachother.users.dto.NEOAdditionalFanInfoRequest;
import com.neo.needeachother.users.dto.NEOChangeableInfoDTO;
import com.neo.needeachother.users.dto.NEOAdditionalStarInfoRequest;
import com.neo.needeachother.users.dto.NEOUserInformationDTO;
import com.neo.needeachother.users.enums.NEOUserApiOrder;
import org.springframework.http.ResponseEntity;

public interface NEOUserInformationService {
     NEOUserInformationDTO doCreateNewStarInformationOrder(NEOAdditionalStarInfoRequest createStarInfoRequest, NEOUserApiOrder userOrder);
     NEOUserInformationDTO doCreateNewFanInformationOrder(NEOAdditionalFanInfoRequest createFanInfoRequest, NEOUserApiOrder userOrder);
     NEOUserInformationDTO doChangePartialInformationOrder(String userID, NEOUserApiOrder userOrder, NEOChangeableInfoDTO changeInfoDto);
     void doDeleteUserInformationOrder(String userID, NEOUserApiOrder userOrder);
     NEOUserInformationDTO doGetStarInformationOrder(String userID, boolean isPrivacy, boolean isDetail, NEOUserApiOrder userOrder);
     NEOUserInformationDTO doGetFanInformationOrder(String userID, boolean isPrivacy, NEOUserApiOrder userOrder);
}

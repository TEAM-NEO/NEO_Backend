package com.neo.needeachother.users.exception;

import com.neo.needeachother.common.enums.NEODomainType;
import com.neo.needeachother.common.enums.NEOErrorCode;
import com.neo.needeachother.common.exception.NEOExpectedException;
import com.neo.needeachother.common.response.NEOErrorResponse;
import com.neo.needeachother.users.controller.NEOUserInformationController;
import lombok.Getter;

import java.util.List;

@Getter
public class NEOUserExpectedException extends NEOExpectedException {

    private final NEOUserInformationController.NEOUserOrder userOrder;

    public NEOUserExpectedException(NEOErrorCode errorCode, NEOUserInformationController.NEOUserOrder userOrder) {
        super(NEODomainType.USERS, errorCode, userOrder.getFailMessage());
        this.userOrder = userOrder;
    }

    public NEOUserExpectedException(NEOErrorCode errorCode, String errorDetailMessage, NEOUserInformationController.NEOUserOrder userOrder){
        super(NEODomainType.USERS, errorCode, errorDetailMessage, userOrder.getFailMessage());
        this.userOrder = userOrder;
    }

    public NEOUserExpectedException(NEOErrorResponse errorResponse, NEOUserInformationController.NEOUserOrder userOrder){
        super(NEODomainType.USERS, errorResponse, userOrder.getFailMessage());
        this.userOrder = userOrder;
    }

    public NEOUserExpectedException(List<NEOErrorResponse> errorResponseList, NEOUserInformationController.NEOUserOrder userOrder){
        super(NEODomainType.USERS, errorResponseList, userOrder.getFailMessage());
        this.userOrder = userOrder;
    }

}

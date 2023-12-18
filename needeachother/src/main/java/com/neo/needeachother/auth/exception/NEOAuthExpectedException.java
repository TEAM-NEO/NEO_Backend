package com.neo.needeachother.auth.exception;

import com.neo.needeachother.common.enums.NEODomainType;
import com.neo.needeachother.common.enums.NEOErrorCode;
import com.neo.needeachother.common.exception.NEOExpectedException;
import com.neo.needeachother.common.response.NEOErrorResponse;
import com.neo.needeachother.users.enums.NEOUserApiOrder;
import lombok.Getter;

import java.util.List;


@Getter
public class NEOAuthExpectedException extends NEOExpectedException {

    private final NEOUserApiOrder userOrder;

    public NEOAuthExpectedException(NEOErrorCode errorCode, NEOUserApiOrder userOrder) {
        super(NEODomainType.AUTH, errorCode, userOrder.getFailMessage());
        this.userOrder = userOrder;
    }

    public NEOAuthExpectedException(NEOErrorCode errorCode, String errorDetailMessage, NEOUserApiOrder userOrder) {
        super(NEODomainType.AUTH, errorCode, errorDetailMessage, userOrder.getFailMessage());
        this.userOrder = userOrder;
    }

    public NEOAuthExpectedException(NEOErrorResponse errorResponse, NEOUserApiOrder userOrder) {
        super(NEODomainType.AUTH, errorResponse, userOrder.getFailMessage());
        this.userOrder = userOrder;
    }

    public NEOAuthExpectedException(List<NEOErrorResponse> errorResponseList, NEOUserApiOrder userOrder) {
        super(NEODomainType.AUTH, errorResponseList, userOrder.getFailMessage());
        this.userOrder = userOrder;
    }
}
package com.neo.needeachother.common.exception;

import com.neo.needeachother.common.enums.NEODomainType;
import com.neo.needeachother.common.enums.NEOErrorCode;
import com.neo.needeachother.common.response.NEOErrorResponse;
import lombok.Getter;

import java.util.*;

@Getter
public class NEOExpectedException extends RuntimeException {

    private final NEODomainType domainType;
    private final List<NEOErrorResponse> errorResponseList;

    public NEOExpectedException(NEODomainType domainType, NEOErrorCode errorCode, String message){
        this(domainType, errorCode, null, message);
    }

    public NEOExpectedException(NEODomainType domainType, NEOErrorCode errorCode, String errorDetailMessage, String message) {
        this(domainType,
                NEOErrorResponse.builder()
                        .neoErrorCode(errorCode)
                        .errorDetail(errorDetailMessage).build(),
                message);
    }

    public NEOExpectedException(NEODomainType domainType, NEOErrorResponse errorResponse, String message) {
        this(domainType, List.of(errorResponse), message);
    }

    public NEOExpectedException(NEODomainType domainType, List<NEOErrorResponse> errorResponseList, String message) {
        super(message);
        if(errorResponseList == null || errorResponseList.size() == 0){
            throw new NEOUnexpectedException("NEOExpectedException을 생성하기 위한 List<NEOErrorResponse> 타입의 값이 null이거나 비어있음.");
        }
        this.domainType = domainType;
        this.errorResponseList = errorResponseList;
    }

}

package com.neo.needeachother.common.exception;

import com.neo.needeachother.common.enums.NEODomainType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class NEOExpectedException extends RuntimeException{

    private final NEODomainType domainType;

    public NEOExpectedException(NEODomainType domainType){
        super();
        this.domainType = domainType;
    }

    public NEOExpectedException(NEODomainType domainType, String message){
        super(message);
        this.domainType = domainType;
    }
}

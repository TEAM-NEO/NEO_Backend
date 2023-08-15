package com.neo.needeachother.users.exception;

import com.neo.needeachother.common.enums.NEODomainType;
import com.neo.needeachother.common.exception.NEOExpectedException;

public class NEOUserExpectedException extends NEOExpectedException {

    public NEOUserExpectedException(String message) {
        super(NEODomainType.USERS, message);
    }

}

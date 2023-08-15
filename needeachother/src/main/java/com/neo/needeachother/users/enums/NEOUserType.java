package com.neo.needeachother.users.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NEOUserType {
    FAN(TypeCode.FAN),
    STAR(TypeCode.STAR);

    private final String typeCode;

    public static final class TypeCode{
        public static final String FAN = "F";
        public static final String STAR = "S";
    }
}

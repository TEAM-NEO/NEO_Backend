package com.neo.needeachother.users.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * @author 이승훈<br>
 * @since 23.08.21<br>
 * NEO의 사용자 유형을 정의합니다.
 */
@Getter
@RequiredArgsConstructor
public enum NEOUserType {
    FAN(TypeCode.FAN, "팬", "ROLE_FAN"),
    STAR(TypeCode.STAR, "스타", "ROLE_STAR"),
    GUEST(TypeCode.GUEST, "임시", "ROLE_GUEST");

    private final String typeCode;
    private final String description;
    private final String key;

    public static final class TypeCode{
        public static final String FAN = "F";
        public static final String STAR = "S";
        public static final String GUEST = "G";
    }

    /**
     * 데이터베이스에 저장되는 타입 코드를 Enum으로 변경하는 메소드입니다.
     * @param typeCode db의 데이터
     * @return {@code NEOUserType}
     */
    public static NEOUserType convertTypeCodeToEnum(String typeCode){
        return Arrays.stream(NEOUserType.values())
                .filter(userType -> userType.getTypeCode().equals(typeCode))
                .findAny()
                .orElseThrow();
    }
}

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
    FAN(TypeCode.FAN, "팬"),
    STAR(TypeCode.STAR, "스타"),
    GUEST(TypeCode.GUEST, "익명");

    private final String typeCode;
    private final String description;

    /* @DiscriminatorValue에 사용 */
    public static final class TypeCode{
        public static final String FAN = "F";
        public static final String STAR = "S";
        public static final String GUEST = "G";
    }

    public static NEOUserType convertTypeCodeToEnum(String typeCode){
        return Arrays.stream(NEOUserType.values())
                .filter(userType -> userType.getTypeCode().equals(typeCode))
                .findAny()
                .orElseThrow();
    }
}

package com.neo.needeachother.users.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author 이승훈<br>
 * @since 23.08.21<br>
 * NEO의 사용자 유형을 정의합니다.
 */
@Getter
@RequiredArgsConstructor
public enum NEOUserType {
    FAN(TypeCode.FAN),
    STAR(TypeCode.STAR);

    private final String typeCode;

    /* @DiscriminatorValue에 사용 */
    public static final class TypeCode{
        public static final String FAN = "F";
        public static final String STAR = "S";
    }
}

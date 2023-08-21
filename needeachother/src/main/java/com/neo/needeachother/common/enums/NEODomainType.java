package com.neo.needeachother.common.enums;

/**
 * @author 이승훈<br>
 * @since 23.08.21<br>
 * NEO의 도메인 타입을 구분할 수 있는 Enum 타입입니다.<br>
 * 대표적으로 {@code NEOExpectedException}에서 어느 도메인에서 발생한 에러인지 판단 할 수 있도록 필드로 포함하고 있습니다.
 */
public enum NEODomainType {
    AUTH,
    USERS,
    CONTENTS,
    QUESTIONS,
    COMMON;
}

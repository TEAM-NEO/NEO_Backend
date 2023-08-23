package com.neo.needeachother.users.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
/**
 * 각 컨트롤러의 엔드포인트 메서드가 고유하게 갖는 값입니다.<br>
 * 해당 enum 값을 통해 어떤 요청인지, 성공시 최종 메시지 및 실패시 최종 메시지를 획득할 수 있습니다.
 */
@Getter
@RequiredArgsConstructor
public enum NEOUserOrder {
    CREATE_STAR_INFO("새로운 스타 정보 생성에 성공했습니다.", "새로운 스타 정보 생성에 실패했습니다.", "POST api/v1/users/stars"),
    CREATE_FAN_INFO("새로운 팬 정보 생성에 성공했습니다.", "새로운 팬 정보 생성에 실패했습니다.", "POST api/v1/users/fans"),
    GET_USER_INFO("사용자 전체 정보를 얻어오는데 성공했습니다.", "사용자 전체 정보를 얻어오는데 실패했습니다.", "GET api/v1/users/{user_id}"),
    GET_USER_PUBLIC_INFO("사용자 공개 정보를 얻어오는데 성공했습니다.", "사용자 공개 정보를 얻어오는데 실패했습니다.", "GET api/v1/users/{user_id}"),
    CHANGE_USER_INFO("사용자 정보 변경에 성공했습니다", "사용자 정보 변경에 실패했습니다.", "PATCH api/v1/users/{user_id}"),
    DELETE_USER_ORDER("사용자 정보 삭제에 성공했습니다.", "사용자 정보 삭제에 실패했습니다.", "DELETE api/v1/users/{user_id}"),
    COMMON(null, null, null);

    private final String successMessage;
    private final String failMessage;
    private final String requestedMethodAndURI;
}
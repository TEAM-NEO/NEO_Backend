package com.neo.needeachother.users.enums;

import com.neo.needeachother.common.enums.NEOResponseCode;
import com.neo.needeachother.users.dto.NEOAdditionalFanInfoRequest;
import com.neo.needeachother.users.dto.NEOChangeableInfoDTO;
import com.neo.needeachother.users.dto.NEOAdditionalStarInfoRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * 각 컨트롤러의 엔드포인트 메서드가 고유하게 갖는 값입니다.<br>
 * 해당 enum 값을 통해 어떤 요청인지, 성공시 최종 메시지 및 실패시 최종 메시지를 획득할 수 있습니다.
 */
@Getter
@RequiredArgsConstructor
public enum NEOUserApiOrder {

    /* USER DOMAIN */
    CREATE_STAR_INFO("POST", "api/v1/users/stars", "새로운 스타 정보 생성에 성공했습니다.",
            "새로운 스타 정보 생성에 실패했습니다.", Pattern.compile("^/api/v1/users/stars$"), NEOAdditionalStarInfoRequest.class),
    CREATE_FAN_INFO("POST", "api/v1/users/fans","새로운 팬 정보 생성에 성공했습니다.",
            "새로운 팬 정보 생성에 실패했습니다.", Pattern.compile("^/api/v1/users/fans$"), NEOAdditionalFanInfoRequest.class),
    GET_STAR_INFO("GET", "api/v1/users/stars/{user_id}", "스타 정보를 성공적으로 반환합니다.",
            "스타 정보를 반환하는데 실패했습니다.", Pattern.compile("^/api/v1/users/stars/[a-zA-Z_0-9]+(\\?privacy=(true|false))?(&detail=(true|false))?$"), null),
    GET_FAN_INFO("GET", "api/v1/users/fans/{user_id}", "팬 정보를 성공적으로 반환합니다.",
            "팬 정보를 반환하는데 실패했습니다.", Pattern.compile("^/api/v1/users/fans/[a-zA-Z_0-9]+(\\?privacy=(true|false))?$"), null),
    CHANGE_USER_INFO("PATCH", "api/v1/users/{user_id}","사용자 정보 변경에 성공했습니다",
            "사용자 정보 변경에 실패했습니다.", Pattern.compile("^/api/v1/users/(stars|fans)/[a-zA-Z_0-9]+$"), NEOChangeableInfoDTO.class),
    DELETE_USER_ORDER("DELETE", "api/v1/users/{user_id}","사용자 정보 삭제에 성공했습니다.",
            "사용자 정보 삭제에 실패했습니다.", Pattern.compile("^/api/v1/users/(stars|fans)/[a-zA-Z_0-9]+$"), null),

    /* AUTH DOMAIN */
    REISSUE_ACCESS_TOKEN_ORDER("POST", "api/v1/token/reissue", "새로운 액세스 토큰 발급에 성공했습니다.",
            "새로운 액세스 토큰 발급에 실패했습니다.", Pattern.compile("^api/v1/token/reissue$"), null),

    NONE(null, null, null, null, null, null);

    private final String httpMethod;
    private final String requestURI;
    private final String successMessage;
    private final String failMessage;
    private final Pattern requestURIRegex;
    private final Class requestBodyClass;

    public String getRequestedMethodAndURI(){
        return this.httpMethod + " " + this.requestURI;
    }

    public static NEOUserApiOrder fromHttpMethodAndRequestURI(String httpMethod, String requestURI){
        return Arrays.stream(NEOUserApiOrder.values())
                .filter(order -> !order.equals(NEOUserApiOrder.NONE))
                .filter(order -> order.getHttpMethod().equals(httpMethod))
                .filter(order -> order.getRequestURIRegex().matcher(requestURI).matches())
                .findAny()
                .orElse(NONE);
    }

}
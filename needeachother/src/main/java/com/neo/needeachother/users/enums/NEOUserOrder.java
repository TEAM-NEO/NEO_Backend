package com.neo.needeachother.users.enums;

import com.neo.needeachother.common.enums.NEOResponseCode;
import com.neo.needeachother.users.dto.NEOChangeableInfoDto;
import com.neo.needeachother.users.dto.NEOFanInfoDto;
import com.neo.needeachother.users.dto.NEOStarInfoDto;
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
public enum NEOUserOrder {

    CREATE_STAR_INFO("POST", "api/v1/users/stars", "새로운 스타 정보 생성에 성공했습니다.",
            "새로운 스타 정보 생성에 실패했습니다.", Pattern.compile("^/api/v1/users/stars$"), NEOStarInfoDto.class),
    CREATE_FAN_INFO("POST", "api/v1/users/fans","새로운 팬 정보 생성에 성공했습니다.",
            "새로운 팬 정보 생성에 실패했습니다.", Pattern.compile("^/api/v1/users/fans$"), NEOFanInfoDto.class),
    GET_USER_INFO("GET", "api/v1/users/{user_id}","사용자 전체 정보를 얻어오는데 성공했습니다.",
            "사용자 전체 정보를 얻어오는데 실패했습니다.", Pattern.compile("^/api/v1/users/[^/]+$"), null),
    GET_USER_PUBLIC_INFO("GET", "api/v1/users/{user_id}","사용자 공개 정보를 얻어오는데 성공했습니다.",
            "사용자 공개 정보를 얻어오는데 실패했습니다.", Pattern.compile("^/api/v1/users/[^/]+$"), null),
    CHANGE_USER_INFO("PATCH", "api/v1/users/{user_id}","사용자 정보 변경에 성공했습니다",
            "사용자 정보 변경에 실패했습니다.", Pattern.compile("^/api/v1/users/[^/]+$"), NEOChangeableInfoDto.class),
    DELETE_USER_ORDER("DELETE", "api/v1/users/{user_id}","사용자 정보 삭제에 성공했습니다.",
            "사용자 정보 삭제에 실패했습니다.", Pattern.compile("^/api/v1/users/[^/]+$"), null),

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

    public HttpHeaders renderHttpHeadersByUserOrderAndResponseCode(NEOResponseCode respCode){
        HttpHeaders headers = new HttpHeaders();
        headers.add("responseCode", respCode.name());
        headers.add("requestedMethodAndURI", this.getRequestedMethodAndURI());
        headers.add("msg", respCode.equals(NEOResponseCode.SUCCESS) ? this.getSuccessMessage() : this.getFailMessage());
        return headers;
    }

    public static NEOUserOrder fromHttpMethodAndRequestURI(String httpMethod, String requestURI){
        return Arrays.stream(NEOUserOrder.values())
                .filter(order -> !order.equals(NEOUserOrder.NONE))
                .filter(order -> order.getHttpMethod().equals(httpMethod))
                .filter(order -> order.getRequestURIRegex().matcher(requestURI).matches())
                .findAny()
                .orElse(NONE);
    }

}
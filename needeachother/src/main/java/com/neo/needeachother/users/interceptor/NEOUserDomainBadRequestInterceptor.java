package com.neo.needeachother.users.interceptor;

import com.neo.needeachother.common.enums.NEOErrorCode;
import com.neo.needeachother.common.enums.NEOResponseCode;
import com.neo.needeachother.common.response.NEOErrorResponse;
import com.neo.needeachother.common.response.NEOFinalErrorResponse;
import com.neo.needeachother.common.service.NEOInterceptorService;
import com.neo.needeachother.users.enums.NEOUserOrder;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;


@Slf4j
@Component
@RequiredArgsConstructor
public class NEOUserDomainBadRequestInterceptor implements HandlerInterceptor {

    private final NEOInterceptorService interceptorService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // HTTP 메소드와 URI를 기반으로 User 도메인의 어떤 요청인지 분류
        NEOUserOrder userOrder = NEOUserOrder.fromHttpMethodAndRequestURI(request.getMethod(), request.getRequestURI());
        log.info("Request Arrived : " + request.getMethod() + " " + request.getRequestURI() + userOrder.name());

        // NEOUserOrder에 매칭되지 않은 API Method와 URI 요청
        if (userOrder.equals(NEOUserOrder.NONE)) {
            log.warn("경고 : NEOUserOrder에 매칭된 API가 없습니다. 정상적인 요청이 아니거나, Enum에 등록되지 않았을 가능성이 있습니다.");
            return false;
        }

        // NEOUserOrder에 등록된 API의 RequestDto를 통해 역직렬화를 위한 propertyName을 얻어옴.
        HashSet<String> jsonPropertyNameSet = interceptorService.getJsonPropertyNames(userOrder.getRequestBodyClass());
        log.info(jsonPropertyNameSet.toString());

        // 요청 본문 데이터를 Map으로 변환
        Map<String, Object> requestBodyMap;

        byte[] byteArray = request.getInputStream().readAllBytes();
        String requestBody = new String(byteArray, StandardCharsets.UTF_8);

        // 해당 API는 원래 요구하는 RequestBody가 없는 경우
        if (requestBody.isEmpty() && jsonPropertyNameSet.isEmpty()) {
            log.info("requestbody not exist");
            return true;
        }

        try {
            requestBodyMap = interceptorService.getRequestBodyMap(requestBody);
        } catch (Exception ex){
            log.warn(ex.getMessage());
            NEOFinalErrorResponse finalErrorResponse = interceptorService.getFinalErrorResponseWithJsonFormatError(userOrder);

            // 응답 설정 및 렌더링 후 요청 반려
            return setHttpResponseWithError(NEOErrorCode.INVALID_JSON_FORMAT_REQUEST.getHttpStatus().value(), finalErrorResponse, response);
        }

        log.info(String.valueOf(requestBodyMap.size()));
        log.info(requestBodyMap.toString());



        // 문제가 되는 경우는, DTO로 역직렬화가 불가능한 경우로 requestBody의 필드가 jsonPropertyNameSet에는 존재하지 않는 경우가 문제. 나머지는 상관 없음.
        List<String> errorJsonFieldNameList = new ArrayList<>();
        for (String paramName : requestBodyMap.keySet()) {
            log.info("param : " + paramName);
            if (!jsonPropertyNameSet.contains(paramName)) {
                errorJsonFieldNameList.add(paramName);
            }
        }

        // 문제가 없는 경우 -> 컨트롤러로
        if (errorJsonFieldNameList.isEmpty()) {
            log.info("문제 없음");
            return true;
        }

        // 최종 에러 응답 생성
        NEOFinalErrorResponse finalErrorResponse = interceptorService.getFinalErrorResponseWithJsonFieldError(errorJsonFieldNameList, userOrder);
        log.info(finalErrorResponse.toString());

        return setHttpResponseWithError(NEOErrorCode.INVALID_JSON_FIELD_NAME.getHttpStatus().value(), finalErrorResponse, response);
    }

    private boolean setHttpResponseWithError(int status, NEOFinalErrorResponse errorResponse, HttpServletResponse response) throws IOException {
        // 응답 설정 및 렌더링 후 요청 반려
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(status);
        response.getWriter().write(interceptorService.convertDtoToWriterValue(errorResponse));
        response.getWriter().flush();
        return false;
    }

}

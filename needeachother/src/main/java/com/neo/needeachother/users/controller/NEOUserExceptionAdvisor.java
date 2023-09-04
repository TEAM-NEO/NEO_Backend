package com.neo.needeachother.users.controller;

import com.neo.needeachother.common.enums.NEOResponseCode;
import com.neo.needeachother.common.exception.NEOUnexpectedException;
import com.neo.needeachother.common.response.NEOErrorResponse;
import com.neo.needeachother.common.response.NEOFinalErrorResponse;
import com.neo.needeachother.users.enums.NEOUserApiOrder;
import com.neo.needeachother.users.exception.NEOUserExpectedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

/**
 * @author 이승훈<br>
 * @since 23.08.24<br>
 * User 도메인에서 발생한 Exception을 컨트롤러 레벨에서 제어할 수 있습니다.
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.neo.needeachother.users.controller")
public class NEOUserExceptionAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NEOUserExpectedException.class})
    public ResponseEntity<NEOFinalErrorResponse> handleUserExceptionFromAPIMethod(NEOUserExpectedException ex){
        log.warn(ex.getMessage());
        return handleUserExceptionInternal(ex);
    }

    @ExceptionHandler(value = {NEOUnexpectedException.class})
    public ResponseEntity<NEOFinalErrorResponse> handleUnexpectedException(NEOUnexpectedException ex){
        log.warn(ex.getMessage());
        return ResponseEntity.internalServerError()
                .body(NEOFinalErrorResponse.builder()
                        .responseCode(NEOResponseCode.FAIL)
                        .msg(ex.getMessage())
                        .errors(List.of(NEOErrorResponse.builder().errorCode(-900).errorDescription("서버 내부 문제 발생").errorDetail("자세한 내용은 서버 측에 문의하세요.").build()))
                        .build());
    }

    private ResponseEntity<NEOFinalErrorResponse> handleUserExceptionInternal(final NEOUserExpectedException ex){
        List<NEOErrorResponse> errorResponseList = ex.getErrorResponseList();
        NEOUserApiOrder userOrder = ex.getUserOrder();

        return ResponseEntity.status(ex.getErrorResponseList().get(0).getNeoErrorCode().getHttpStatus())
                .body(renderResponseBody(errorResponseList, userOrder));
    }

    private NEOFinalErrorResponse renderResponseBody(final List<NEOErrorResponse> errorResponseList, final NEOUserApiOrder userOrder){
        return NEOFinalErrorResponse.builder()
                .requestedMethodAndURI(userOrder.getRequestedMethodAndURI())
                .responseCode(NEOResponseCode.FAIL)
                .msg(userOrder.getFailMessage())
                .errors(errorResponseList)
                .build();
    }

}

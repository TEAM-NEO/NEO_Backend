package com.neo.needeachother.auth.handler;

import com.neo.needeachother.common.enums.NEOErrorCode;
import com.neo.needeachother.common.enums.NEOResponseCode;
import com.neo.needeachother.common.response.NEOErrorResponse;
import com.neo.needeachother.common.response.NEOFinalErrorResponse;
import com.neo.needeachother.common.util.NEOServletResponseWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class NEOOAuth2FailureHandler implements AuthenticationFailureHandler {

    private final NEOServletResponseWriter servletResponseWriter;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        servletResponseWriter.writeResponseWithErrorAndStatusCode(response,
                NEOFinalErrorResponse.builder()
                        .msg("소셜 로그인 실패! 서버 로그 및 errors 필드를 확인해주세요.")
                        .requestedMethodAndURI(request.getMethod() + " " + request.getRequestURI())
                        .responseCode(NEOResponseCode.FAIL)
                        .errors(List.of(NEOErrorResponse.fromErrorCodeWithDetail(NEOErrorCode.FAIL_IN_OAUTH, exception.getMessage())))
                        .build(),
                HttpServletResponse.SC_UNAUTHORIZED);
    }

}

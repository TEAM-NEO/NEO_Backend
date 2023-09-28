package com.neo.needeachother.auth.handler;

import com.neo.needeachother.auth.dto.NEOCustomOAuth2User;
import com.neo.needeachother.auth.dto.NEORefreshToken;
import com.neo.needeachother.auth.service.NEOTokenService;
import com.neo.needeachother.common.util.NEOServletResponseWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class NEOOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final NEOTokenService tokenService;
    private final NEOServletResponseWriter servletResponseWriter;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 Login 성공!");

        NEOCustomOAuth2User oAuth2User = (NEOCustomOAuth2User) authentication.getPrincipal();
        loginSuccess(response, oAuth2User);
    }

    private void loginSuccess(HttpServletResponse response, NEOCustomOAuth2User oAuth2User) throws IOException {
        // 새로운 accessToken 생성
        String accessToken = tokenService.createAccessToken(oAuth2User.getEmail());

        // 새로운 Refresh Token 생성
        NEORefreshToken refreshToken = tokenService.createRefreshToken(oAuth2User.getEmail());

        // 기존 Refresh Token이 존재한다면 폐기
        tokenService.deleteRefreshTokenByEmail(oAuth2User.getEmail());

        tokenService.saveRefreshToken(refreshToken);
        tokenService.sendAccessAndRefreshToken(response, accessToken, refreshToken.getRefreshToken());
        servletResponseWriter.writeResponseWithBodyAndStatusCode(response, "소셜 로그인에 성공했습니다. 발급된 토큰을 잘 관리해주세요.", HttpStatus.OK.value());
    }

}

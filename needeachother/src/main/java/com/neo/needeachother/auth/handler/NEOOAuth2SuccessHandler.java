package com.neo.needeachother.auth.handler;

import com.neo.needeachother.auth.dto.NEOCustomOAuth2User;
import com.neo.needeachother.auth.dto.NEORefreshToken;
import com.neo.needeachother.auth.repository.NEORefreshTokenRepository;
import com.neo.needeachother.auth.service.NEOTokenService;
import com.neo.needeachother.users.enums.NEOUserType;
import com.neo.needeachother.users.repository.NEOUserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final NEOTokenService jwtService;
    private final NEORefreshTokenRepository refreshTokenRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 Login 성공!");

        NEOCustomOAuth2User oAuth2User = (NEOCustomOAuth2User) authentication.getPrincipal();
        loginSuccess(response, oAuth2User);
    }

    private void loginSuccess(HttpServletResponse response, NEOCustomOAuth2User oAuth2User) throws IOException {
        String accessToken = jwtService.createAccessToken(oAuth2User.getEmail());

        NEORefreshToken refreshToken = jwtService.createRefreshToken(oAuth2User.getEmail());
        Map<String, String> body = new HashMap<>();
        body.put("msg", "소셜 로그인에 성공했습니다.");

        refreshTokenRepository.save(refreshToken);
        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken.getRefreshToken());

    }

}

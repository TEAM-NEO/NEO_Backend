package com.neo.needeachother.config;

import com.neo.needeachother.auth.filter.NEOJwtAuthenticationFilter;
import com.neo.needeachother.auth.handler.NEOOAuth2FailureHandler;
import com.neo.needeachother.auth.handler.NEOOAuth2SuccessHandler;
import com.neo.needeachother.auth.repository.NEORefreshTokenRepository;
import com.neo.needeachother.auth.service.NEOOAuth2UserService;
import com.neo.needeachother.auth.service.NEOTokenService;
import com.neo.needeachother.common.util.NEOServletResponseWriter;
import com.neo.needeachother.users.filter.NEOUserDomainBadRequestFilter;
import com.neo.needeachother.users.repository.NEOUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class NEOSecurityConfig {

    private final NEOTokenService tokenService;
    private final NEOUserRepository userRepository;
    private final NEORefreshTokenRepository refreshTokenRepository;
    private final NEOServletResponseWriter servletResponseWriter;
    private final NEOOAuth2SuccessHandler oAuth2LoginSuccessHandler;
    private final NEOOAuth2FailureHandler oAuth2LoginFailureHandler;
    private final NEOOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 폼 로그인 사용 X
        http.formLogin(AbstractHttpConfigurer::disable)
                // httpBasic 인증 방식 사용 X
                .httpBasic(AbstractHttpConfigurer::disable)
                // 브라우저를 사용하지 않는다는 전제 하에 csrf X (NEO는 앱)
                .csrf(AbstractHttpConfigurer::disable)
                // 세션 미사용, JWT Token 방식 사용
                .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // OAuth2 소셜 로그인
                .oauth2Login((oauth2Login) -> oauth2Login
                        .loginProcessingUrl("/api/v1/oauth2/*")
                        .successHandler(oAuth2LoginSuccessHandler)
                        .failureHandler(oAuth2LoginFailureHandler)
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(customOAuth2UserService)))
                // URL별 권한 관리
                .authorizeHttpRequests(requests -> requests
                        // 로그인 관련 URL 모두 허가
                        .requestMatchers("/login", "/oauth2/authorization/**", "/api/v1/oauth2/**").permitAll()
                        // API 개발 문서 URL 모두 허가
                        .requestMatchers("/docs/**").permitAll()
                        .requestMatchers("/api/v1/post/**").permitAll()
                        // 나머지 URL
                        .anyRequest().authenticated())
                .addFilterAfter(jwtAuthenticationProcessingFilter(), LogoutFilter.class);

        return http.build();
    }

    public NEOJwtAuthenticationFilter jwtAuthenticationProcessingFilter() {
        return new NEOJwtAuthenticationFilter(tokenService, userRepository, servletResponseWriter);
    }
}

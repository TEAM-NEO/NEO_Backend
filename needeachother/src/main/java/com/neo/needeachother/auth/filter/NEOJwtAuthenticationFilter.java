package com.neo.needeachother.auth.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neo.needeachother.auth.dto.NEORefreshToken;
import com.neo.needeachother.auth.repository.NEORefreshTokenRepository;
import com.neo.needeachother.auth.service.NEOTokenService;
import com.neo.needeachother.auth.util.NEOPasswordUtil;
import com.neo.needeachother.common.enums.NEOErrorCode;
import com.neo.needeachother.common.enums.NEOResponseCode;
import com.neo.needeachother.common.response.NEOErrorResponse;
import com.neo.needeachother.common.response.NEOFinalErrorResponse;
import com.neo.needeachother.common.util.NEOServletResponseWriter;
import com.neo.needeachother.users.entity.NEOUserEntity;
import com.neo.needeachother.users.repository.NEOUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class NEOJwtAuthenticationFilter extends OncePerRequestFilter {

    private final NEOTokenService tokenService;
    private final NEORefreshTokenRepository refreshTokenRepository;
    private final NEOUserRepository userRepository;
    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
    private final NEOServletResponseWriter servletResponseWriter;
    private static final String[] NO_CHECK_URLS = {"/login", "/oauth2/authorization/naver", "/oauth2/authorization/google", "/oauth2/authorization/kakao",
            "/docs/neo-api-guide.html", "/api/v1/oauth2/naver", "/api/v1/oauth2/kakao", "/api/v1/oauth2/google", "/favicon.ico"};

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 요청 헤더에서 "Access Token"을 얻고 유효성 및 만료 기간 검사를 진행합니다.
        String accessToken = tokenService.extractAccessToken(request)
                .filter(tokenService::isTokenValid)
                .orElse(null);

        String accessTokenEmail = tokenService.extractEmail(accessToken)
                .orElse(null);

        // 토큰이 유효하지 않은 경우
        if (accessToken == null || accessTokenEmail == null) {
            rejectInvalidAccessToken(request, response);
            return;
        }

        // "Access Token"이 유효하지만 만료된 경우
        if (tokenService.isTokenExpired(accessToken)) {
            checkRefreshTokenAndReIssueAccessToken(request, response, accessTokenEmail);
            return;
        }

        // "Access Token"이 유효하고, 만료되지 않은 경우
        processAuthentication(request, response, filterChain);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        log.info("request url : " + request.getRequestURI());

        // JWT 토큰을 헤더에 포함하지 않아도 되는 "URL"인지 검춣합니다.
        return Arrays.stream(NO_CHECK_URLS)
                .anyMatch(url -> request.getRequestURI().equals(url));
    }

    /**
     * 유효하지 않은 "AccessToken"이 헤더로 들어왔다면, 요청을 거절합니다.<br>
     *
     * @param response {@code HttpStatus.UNAUTHORIZED}를 포함한 응답
     */
    public void rejectInvalidAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 유효하지 않은 Access Token 에 대한 요청을 거부하는 응답 생성
        NEOFinalErrorResponse finalErrorResponse = NEOFinalErrorResponse.builder()
                .requestedMethodAndURI(request.getMethod() + " " + request.getRequestURI())
                .responseCode(NEOResponseCode.FAIL)
                .msg("올바르지 않은 사용자로 분류되어 요청을 진행할 수 없습니다.")
                .errors(List.of(NEOErrorResponse.fromErrorCodeWithDetail(NEOErrorCode.INVALID_ACCESS_TOKEN, "오류 대상 : authorization header")))
                .build();

        // 해당 응답과 상태 코드를 반영
        servletResponseWriter.writeResponseWithErrorAndStatusCode(response, finalErrorResponse, NEOErrorCode.INVALID_ACCESS_TOKEN.getHttpStatus().value());
    }

    /**
     * "Refresh Token"을 체크한 후, 존재하지 않는다면 재로그인을 유도합니다.<br>
     * 존재한다면 현재 요청을 거절한 후, 헤더에 새롭게 생성한 "Access Token"을 담아 에러 응답을 보냅니다. <br>
     *
     * @param request  HTTP Request
     * @param response {@code HttpStatus.UNAUTHORIZED}의 sc를 가진 응답
     * @param accessTokenEmail    Refresh Token의 key인 email
     * @throws IOException DTO -> JSON 변환 과정 발생 예외
     */
    public void checkRefreshTokenAndReIssueAccessToken(HttpServletRequest request, HttpServletResponse response, String accessTokenEmail) throws IOException {
        // 요청으로부터 토큰을 획득하고 레디스에 존재하는지 확인.
        String refreshTokenEmail = tokenService.extractRefreshToken(request)
                .flatMap(refreshTokenRepository::findByUUIDToken)
                .map(NEORefreshToken::getEmail)
                .orElse(null);

        // 레디스의 리프레시 토큰이 만료 혹은 "Access Token"의 이메일과 일치하지 않는 경우 재로그인
        if (!accessTokenEmail.equals(refreshTokenEmail)) {
            NEOFinalErrorResponse noRefreshTokenErrorResponse = NEOFinalErrorResponse.builder()
                    .requestedMethodAndURI(request.getMethod() + " " + request.getRequestURI())
                    .responseCode(NEOResponseCode.FAIL)
                    .msg("토큰 만료로 인한 재로그인이 필요합니다.")
                    .errors(List.of(NEOErrorResponse.fromErrorCodeWithDetail(NEOErrorCode.EXPIRED_REFRESH_TOKEN,
                            "오류 대상 : " + tokenService.getRefreshHeader() + " header")))
                    .build();
            servletResponseWriter.writeResponseWithErrorAndStatusCode(response, noRefreshTokenErrorResponse, NEOErrorCode.EXPIRED_REFRESH_TOKEN.getHttpStatus().value());
            return;
        }

        // 리프레시 토큰이 유효하고 만료되지 않은 경우 "Access Token"을 재발급합니다.
        String reIssuedAccessToken = tokenService.createAccessToken(refreshTokenEmail);
        NEOFinalErrorResponse reIssuedAccessTokenErrorResponse = NEOFinalErrorResponse.builder()
                .requestedMethodAndURI(request.getMethod() + " " + request.getRequestURI())
                .responseCode(NEOResponseCode.FAIL)
                .msg("NEO에 접근 가능한 Access Token을 재발급합니다.")
                .errors(List.of(NEOErrorResponse.fromErrorCodeWithDetail(NEOErrorCode.EXPIRED_ACCESS_TOKEN,
                        "오류 대상 : " + tokenService.getAccessHeader() + " header")))
                .build();
        setHttpErrorResponseWithNewToken(reIssuedAccessTokenErrorResponse, reIssuedAccessToken, response);
    }

    /**
     * "Access Token"이 유효한 경우, 해당 요청을 받아들입니다. <br>
     * {@code Authentication} 객체를 생성한 뒤 다음 필터로 진행시킵니다. <br>
     * @param request 사용자 HTTP 요청
     * @param response HTTP 응답
     * @param filterChain NEO 필터 체인
     * @throws ServletException
     * @throws IOException
     */
    public void processAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 토큰으로부터 이메일을 확보해 Security에서 사용할 Authentication 객체를 생성해 Context에 등록합니다.
        tokenService.extractAccessToken(request)
                .filter(tokenService::isTokenValid)
                .flatMap(tokenService::extractEmail)
                .flatMap(userRepository::findByEmail)
                .ifPresent(this::saveAuthentication);

        // 필터를 통과해 다음 필터 혹은 다음 과정으로 이동합니다.
        filterChain.doFilter(request, response);
    }

    /**
     * 새로운 "Access Token"을 발급하는 경우에 사용됩니다. <br>
     * NEOFinalErrorResponse를 Body에 담은 응답을 내보냅니다.<br>
     * status code는 NEOFinalErrorResponse의 List<NEOErrorResponse>의 가장 첫 번째 원소의 값으로 결정됩니다.<br>
     * 헤더에 {@code reIssuedAccessToken}이 포함됩니다.
     * @param errorResponse 새로운 Access Token 발급으로 요청 거절 응답
     * @param reIssuedAccessToken 새로 발급된 Access Token
     * @param response 실제 HTTP 응답으로 errorResponse가 포함
     * @throws IOException
     */
    private void setHttpErrorResponseWithNewToken(NEOFinalErrorResponse errorResponse, String reIssuedAccessToken, HttpServletResponse response) throws IOException {
        tokenService.sendAccessToken(response, reIssuedAccessToken);
        servletResponseWriter.writeResponseWithErrorAndStatusCode(response, errorResponse, errorResponse.getErrors().get(0).getNeoErrorCode().getHttpStatus().value());
    }

    public void saveAuthentication(NEOUserEntity neoUser) {
        String password = neoUser.getUserPW();
        if (password == null) { // 소셜 로그인 유저의 비밀번호 임의로 설정 하여 소셜 로그인 유저도 인증 되도록 설정
            password = NEOPasswordUtil.generateRandomPassword();
        }

        UserDetails userDetailsUser = User.builder()
                .username(neoUser.getEmail())
                .password(password)
                .roles(neoUser.getUserType().getKey())
                .build();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetailsUser, null,
                        authoritiesMapper.mapAuthorities(userDetailsUser.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}

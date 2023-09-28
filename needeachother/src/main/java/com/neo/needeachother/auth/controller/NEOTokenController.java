package com.neo.needeachother.auth.controller;

import com.neo.needeachother.auth.service.NEOTokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class NEOTokenController {

    private final NEOTokenService tokenService;

    @PostMapping("/token/reissue")
    public ResponseEntity<String> reIssueRefreshTokenOrder(HttpServletRequest request) {
        String email = tokenService.extractAccessToken(request)
                .flatMap(tokenService::extractEmail)
                .orElseThrow();

        return ResponseEntity.ok()
                .header(tokenService.getAccessHeader(), tokenService.createAccessToken(email))
                .body("새로운 Access Token이 발급되었습니다.");
    }
}

package com.neo.needeachother.auth.controller;

import com.neo.needeachother.auth.service.NEOGoogleOAuth2Service;
import com.neo.needeachother.auth.service.NEOKakaoOAuth2Service;
import com.neo.needeachother.auth.service.NEONaverOAuth2Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class NEOOAuth2Controller {

    private final NEONaverOAuth2Service naverOAuth2Service;
    private final NEOKakaoOAuth2Service kakaoOAuth2Service;
    private final NEOGoogleOAuth2Service googleOAuth2Service;

//    @GetMapping("/login/kakao")
//    public void kakaoOAuthLoginOrder(){
//        return WebClient.builder()
//                .baseUrl("https://kauth.kakao.com/oauth/token")
//                .build()
//                .get()
//                .header("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
//                .retrieve()
//                .bodyToMono(JSONObject.class);
//    }
//
//    @GetMapping("/login/naver")
//    public void naverOAuthLoginOrder(){
//        return WebClient.builder()
//                .baseUrl("https://nid.naver.com/oauth2.0/authorize?client_id={클라이언트 아이디}&response_type=code&redirect_uri={개발자 센터에 등록한 콜백 URL(URL 인코딩)}&state={상태 토큰}")
//                .build()
//                .get()
//                .header("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
//                .retrieve()
//                .bodyToMono(JSONObject.class);
//    }

//    @GetMapping("/oauth2/kakao")
//    public ResponseEntity<String> kakaoOAuthRedirectOrder(@RequestParam String code){
//        log.info("[+| Kakao Login AccessToken :: " + code);
//        return ResponseEntity.ok(code);
//    }
//
//    @GetMapping("/oauth2/naver")
//    public ResponseEntity<String> naverOAuthRedirectOrder(@RequestParam String code){
//        log.info("[+| Naver Login AccessToken :: " + code);
//        return ResponseEntity.ok(code);
//    }
//
//    @GetMapping("/oauth2/google")
//    public ResponseEntity<String> googleOAuthRedirectOrder(@RequestParam String code){
//        log.info("[+| Google Login AccessToken :: " + code);
//        return ResponseEntity.ok(code);
//    }
}

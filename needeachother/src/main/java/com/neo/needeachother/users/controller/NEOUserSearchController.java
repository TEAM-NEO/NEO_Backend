package com.neo.needeachother.users.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 이승훈<br>
 * @since 23.08.21<br>
 * 유저를 검색할 수 있는 엔드포인트 입니다.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class NEOUserSearchController {

    @GetMapping("/stars")
    public void searchStarOrder(){

    }

}

package com.neo.needeachother.users.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


/**
 * @author 이승훈<br>
 * @since 23.08.07<br>
 * 내가 구독한 스타와 관련된 API 와 관련된 엔드포인트입니다.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class NEOFolloweeController {

    @GetMapping("/{user_id}/followee")
    public void getMyStarOrder(@PathVariable("user_id") String userID){

    }

    @PostMapping("/{user_id}/followee")
    public void followStarOrder(@PathVariable("user_id") String userID){

    }

    @DeleteMapping("/{user_id}/followee")
    public void unFollowStarOrder(@PathVariable("user_id") String userID){

    }

}

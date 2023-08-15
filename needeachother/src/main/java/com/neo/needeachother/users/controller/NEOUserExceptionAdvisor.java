package com.neo.needeachother.users.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice(basePackages = "com.neo.needeachother.users.controller")
public class NEOUserExceptionAdvisor extends ResponseEntityExceptionHandler {

}

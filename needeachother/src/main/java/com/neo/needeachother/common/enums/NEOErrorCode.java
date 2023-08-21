package com.neo.needeachother.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum NEOErrorCode {

    /* BAD REQUEST (잘못된 요청) */
    BLANK_VALUE(-100, "입력된 값이 null이거나 값이 비어있습니다.", HttpStatus.BAD_REQUEST),
    MIN_INVALID_USER_ID(-101, "아이디는 최소 4글자입니다.", HttpStatus.BAD_REQUEST),
    MIN_INVALID_USER_PW(-103, "비밀번호는 최소 4글자입니다.", HttpStatus.BAD_REQUEST),
    INVALID_FORMAT_EMAIL(-105, "이메일의 형식이 옳지 않습니다.", HttpStatus.BAD_REQUEST),
    NOT_ONLY_KOR_USERNAME(-107, "사용자 이름은 한국어만 입력 가능합니다.", HttpStatus.BAD_REQUEST),
    MIN_INVALID_USERNAME(-108, "사용자 이름은 최소 2글자입니다.", HttpStatus.BAD_REQUEST),
    INVALID_FORMAT_PHONE_NUMBER(-110, "입력한 핸드폰번호가 형식에 맞지 않습니다.", HttpStatus.BAD_REQUEST),
    INVALID_FORMAT_GENDER(-111, "유효하지 않은 성별 식별자를 입력했습니다.", HttpStatus.BAD_REQUEST),
    INVALID_FORMAT_STAR_CLASSIFICATION(-112, "유효하지 않은 스타 구분자를 입력했습니다.", HttpStatus.BAD_REQUEST),

    /* NOT FOUND (없는 데이터 요청) */
    NOT_EXIST_STAR_ID(-200, "스타 아이디가 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    NOT_EXIST_USER(-201, "해당 유저가 존재하지 않습니다.", HttpStatus.NOT_FOUND);

    private final int errorCode;
    private final String errorDescription;
    private final HttpStatus httpStatus;

    public static final class ValidationMessage{
        public static final String BLANK_VALUE = "BLANK_VALUE";
        public static final String MIN_INVALID_USER_ID = "MIN_INVALID_USER_ID";
        public static final String MIN_INVALID_USER_PW = "MIN_INVALID_USER_PW";
        public static final String INVALID_FORMAT_EMAIL = "INVALID_FORMAT_EMAIL";
        public static final String NOT_ONLY_KOR_USERNAME = "NOT_ONLY_KOR_USERNAME";
        public static final String MIN_INVALID_USERNAME = "MIN_INVALID_USERNAME";
        public static final String INVALID_FORMAT_PHONE_NUMBER = "INVALID_FORMAT_PHONE_NUMBER";
        public static final String INVALID_FORMAT_GENDER = "INVALID_FORMAT_GENDER";
        public static final String INVALID_FORMAT_STAR_CLASSIFICATION = "INVALID_FORMAT_STAR_CLASSIFICATION";
    }
}

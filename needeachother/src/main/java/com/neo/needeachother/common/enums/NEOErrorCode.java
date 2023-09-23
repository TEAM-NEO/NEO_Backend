package com.neo.needeachother.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * @author 이승훈<br>
 * @since 23.08.21<br>
 * NEO에서 예측 가능한 범위의 에러에 대한 에러코드를 정의했습니다.<br>
 * 대표적으로 {@code NEOExpectedException}에서 에러코드를 포함합니다.
 */
@Getter
@RequiredArgsConstructor
public enum NEOErrorCode implements NEODocumentAbleEnum, NEONumberCodeEnum {

    /* BAD REQUEST (잘못된 요청, 형식) */
    INVALID_JSON_FORMAT_REQUEST(-98, "잘못된 JSON 포맷의 요청이 왔습니다.", HttpStatus.BAD_REQUEST),
    INVALID_JSON_FIELD_NAME(-99, "서버에서 요구하는 JSON 필드명과 매칭되지 않는 필드명이 존재합니다.", HttpStatus.BAD_REQUEST),

    /* BAD REQUEST (잘못된 요청, 데이터 측면) */
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
    NOT_EXIST_USER(-201, "해당 유저가 존재하지 않습니다.", HttpStatus.NOT_FOUND),

    /* CONFLICT (데이터에는 문제가 없지만, 내부적 문제 ex.unique value but duplicated)*/
    ALREADY_EXIST_USER(-300, "이미 존재하는 회원입니다.", HttpStatus.CONFLICT),

    /* UNAUTHORIZED */
    EXPIRED_ACCESS_TOKEN(-400, "만료된 토큰입니다. 재발급된 토큰을 헤더에서 확인하세요.", HttpStatus.UNAUTHORIZED),
    EXPIRED_REFRESH_TOKEN(-401, "재발급 토큰이 만료되었습니다. 다시 로그인 하세요.", HttpStatus.UNAUTHORIZED),
    INVALID_ACCESS_TOKEN(-402, "유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED)
    ;

    private final int errorCode;
    private final String errorDescription;
    private final HttpStatus httpStatus;

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public String getDescription() {
        return this.errorDescription;
    }

    @Override
    public String getNumberStringCode() {
        return String.valueOf(this.errorCode);
    }

    /**
     * @author 이승훈<br>
     * @since 23.08.21<br>
     * {@code @Validated}를 통해 발생한 {@code FieldError}가 가지는 메시지를 스트링 값으로 저장합니다.<br>
     * {@code @NotBlank(message = NEOErrorCode.ValidationMessage.BLANK_VALUE)}와 같이 {@code message}값을 {@code ValidationMessage}의 값으로 넣습니다.<br>
     * {@code Enum.valueOf}를 사용해 해당 메시지로 Enum 인스턴스를 획득할 수 있습니다.
     */
    public static final class ValidationMessage{
        public static final String BLANK_VALUE = "BLANK_VALUE";
        public static final String MIN_INVALID_USER_ID = "MIN_INVALID_USER_ID";
        public static final String MIN_INVALID_USER_PW = "MIN_INVALID_USER_PW";
        public static final String INVALID_FORMAT_EMAIL = "INVALID_FORMAT_EMAIL";
        public static final String NOT_ONLY_KOR_USERNAME = "NOT_ONLY_KOR_USERNAME";
        public static final String MIN_INVALID_USERNAME = "MIN_INVALID_USERNAME";
        public static final String INVALID_FORMAT_PHONE_NUMBER = "INVALID_FORMAT_PHONE_NUMBER";
    }
}

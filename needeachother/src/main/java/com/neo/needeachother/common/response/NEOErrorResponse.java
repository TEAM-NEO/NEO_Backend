package com.neo.needeachother.common.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neo.needeachother.common.enums.NEOErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.FieldError;

/**
 * @author 이승훈<br>
 * @since 23.08.21<br>
 * {@code NEOResponseBody}의 {@code errors} 필드에 포함된 에러 응답 객체를 정의했습니다. <br>
 * 숫자 에러코드와 에러코드의 설명과 상세 설명을 포함하고 있습니다. <br>
 * 최종적으로 응답에서 해당 내용을 직렬화하게 됩니다.
 */
@Getter
@Builder
@RequiredArgsConstructor
public class NEOErrorResponse {

    @JsonIgnore
    private final NEOErrorCode neoErrorCode;

    private final int errorCode;
    private final String errorDescription;
    private final String errorDetail;

    /**
     * {@code @Validated}로 발생한 {@code FieldError}를 통해 {@code NEOErrorResponse}를 생성하는 정적 팩토리 메서드
     * @param error 유효성 검사를 통한 필드 에러
     * @return {@code NEOErrorResponse} 최종 응답에 포함될 에러 응답
     */
    public static NEOErrorResponse fromFieldError(FieldError error){
        NEOErrorCode errorCode = NEOErrorCode.valueOf(error.getDefaultMessage());
        return new NEOErrorResponse(errorCode, errorCode.getErrorCode(), errorCode.getErrorDescription(), error.getField() + " : " + error.getRejectedValue());
    }

}

package com.neo.needeachother.common.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neo.needeachother.common.enums.NEOErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.FieldError;

@Getter
@Builder
@RequiredArgsConstructor
public class NEOErrorResponse {

    @JsonIgnore
    private final NEOErrorCode neoErrorCode;

    private final int errorCode;
    private final String errorDescription;
    private final String errorDetail;

    public static NEOErrorResponse fromFieldError(FieldError error){
        NEOErrorCode errorCode = NEOErrorCode.valueOf(error.getDefaultMessage());
        return new NEOErrorResponse(errorCode, errorCode.getErrorCode(), errorCode.getErrorDescription(), error.getField() + " : " + error.getRejectedValue());
    }

}

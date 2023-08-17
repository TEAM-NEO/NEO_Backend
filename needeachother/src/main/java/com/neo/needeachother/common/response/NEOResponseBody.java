package com.neo.needeachother.common.response;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.neo.needeachother.common.enums.NEOResponseCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@JsonFilter("NEOResponseJsonFilter")
@AllArgsConstructor
public class NEOResponseBody<T> {

    @Builder.Default
    private final LocalDateTime timeStamp = LocalDateTime.now();

    private final String requestedPath;

    private final NEOResponseCode responseCode;

    /* API 요청에 대한 성공 및 실패 메시지 */
    private final String msg;

    /* Conditional Visible */
    private final T data;

    /* Conditional Visible */
    /* 단순 실패 메시지가 아닌 실패 원인 */
    private final List<NEOErrorResponse> errors;

}
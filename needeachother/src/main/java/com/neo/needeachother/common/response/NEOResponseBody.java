package com.neo.needeachother.common.response;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.neo.needeachother.common.enums.NEOResponseCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 이승훈<br>
 * @since 23.08.21<br>
 * NEO의 최종 응답 객체입니다. {@code ResponseEntity<T>}와 함께 사용합니다.<br>
 * @param <T> 응답 DTO
 */
@Getter
@Builder
@JsonFilter("NEOResponseJsonFilter")
@AllArgsConstructor
public class NEOResponseBody<T> {

    @Builder.Default
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
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
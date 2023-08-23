package com.neo.needeachother.common.response;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.neo.needeachother.common.enums.NEOResponseCode;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "NEO의 응답 객체")
public class NEOResponseBody<T> {

    @Schema(description = "NEO의 응답이 발생한 시간", example = "2022-03-28 22:11:44")
    @Builder.Default
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private final LocalDateTime timeStamp = LocalDateTime.now();

    @Schema(description = "요청이 들어온 메소드와 URI", example = "GET api/v1/users/{user_id}")
    private final String requestedMethodAndURI;

    @Schema(description = "요청에 되한 최종 응답 코드", example = "SUCCESS")
    private final NEOResponseCode responseCode;

    /* API 요청에 대한 성공 및 실패 메시지 */
    @Schema(description = "요청에 되한 최종 결과 메시지", example = "회원 정보를 얻어오는 데 성공했습니다.")
    private final String msg;

    /* Conditional Visible */
    @Schema(description = "요청에 대한 응답 데이터")
    private final T data;

    /* Conditional Visible */
    /* 단순 실패 메시지가 아닌 실패 원인 */
    @Schema(description = "요청에 대한 실패에 대한 사유 및 코드")
    private final List<NEOErrorResponse> errors;

}
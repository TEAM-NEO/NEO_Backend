package com.neo.needeachother.users.docs;

import com.neo.needeachother.common.response.NEOFinalErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "기존 유저 정보 삭제",
        description = "회원 탈퇴 이후 남은 정보를 모두 삭제합니다.")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "유저 정보 삭제 성공",
                content = {@Content(schema = @Schema(implementation = NEOFinalErrorResponse.class))}),
        @ApiResponse(responseCode = "404", description = "실패 : 요청 대상을 찾을 수 없음",
                content = {@Content(schema = @Schema(implementation = NEOFinalErrorResponse.class))})
})
public @interface NEODeleteUserInfoOrderDocs {
}

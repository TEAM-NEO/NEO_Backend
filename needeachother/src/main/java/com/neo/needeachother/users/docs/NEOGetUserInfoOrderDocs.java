package com.neo.needeachother.users.docs;

import com.neo.needeachother.common.response.NEOFinalErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@Operation(summary = "유저 정보 획득",
        description = "유저의 정보를 획득합니다. 팬과 스타 모두 동일하게 사용이 가능하며, user_id 값을 통해 해당 대상의 정보를 가져옵니다.<br>" +
                "개인적인 정보 획득 및 공개적인 정보 획득에 대해서는 public 값을 통해 제어할 수 있습니다.",
        parameters = {@Parameter(name = "public", description = "개인 정보 및 공개 정보 여부", example = "false")}
)
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "유저 정보 획득 성공",
                content = {@Content(schema = @Schema(implementation = NEOFinalErrorResponse.class))}),
        @ApiResponse(responseCode = "404", description = "실패 : 요청 대상을 찾을 수 없음",
                content = {@Content(schema = @Schema(implementation = NEOFinalErrorResponse.class))})
})
public @interface NEOGetUserInfoOrderDocs {
}

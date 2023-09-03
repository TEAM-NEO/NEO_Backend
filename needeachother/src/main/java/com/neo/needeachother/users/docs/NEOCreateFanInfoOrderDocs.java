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
@Operation(summary = "새로운 팬 정보 생성",
        description = "OAuth2.0을 통해 회원가입을 한 후, 팬에 대한 추가정보를 입력받을 수 있습니다. <br> " +
                "OAuth2.0가 적용되기 이전에는 회원의 나머지 정보도 받아오는 것을 대체합니다.")
@ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "팬 정보 생성 성공"),
        @ApiResponse(responseCode = "400", description = "실패 : 잘못된 요청",
                content = {@Content(schema = @Schema(implementation = NEOFinalErrorResponse.class))})
})
public @interface NEOCreateFanInfoOrderDocs {
}

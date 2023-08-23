package com.neo.needeachother.users.docs;

import com.neo.needeachother.common.response.NEOResponseBody;
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
@Operation(summary = "기존 유저 정보 변경",
        description = "이미 생성된 유저 정보를 변경합니다. 스타 및 팬 구별없이 사용 가능하며 내부 로직에 의해 판단합니다.<br>" +
                "요청의 일부 로직은 스타에게만 해당되며, 팬이 요청에 해당 필드를 포함하더라도 적용되지 않습니다.<br>" +
                "PATCH 메소드 방식으로, JSON에 포함된 필드만 반영됩니다. 즉, JSON에 필드를 포함하지 않거나 null을 보내면 해당 정보는 변경하지 않습니다.<br>" +
                "리스트 필드의 경우 요소를 추가하고 싶다면 기존의 요소도 함께 포함해서 전달해야 반영됩니다.<br>" +
                "빈 리스트를 전달하면, 초기화의 의미를 갖습니다.")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "유저 정보 변경 성공",
                content = {@Content(schema = @Schema(implementation = NEOResponseBody.class))}),
        @ApiResponse(responseCode = "404", description = "실패 : 요청 대상을 찾을 수 없음",
                content = {@Content(schema = @Schema(implementation = NEOResponseBody.class))})
})
public @interface NEOChangeUserInfoOrderDocs {
}

package com.neo.needeachother.users.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neo.needeachother.common.enums.NEOErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "NEO 스타 유저의 커스텀 자기 소개 정보 객체")
public class NEOCustomStarInformation {

    @NotBlank(message = NEOErrorCode.ValidationMessage.BLANK_VALUE)
    @Schema(description = "스타가 원하는 자기 소개 커스텀 제목", example = "내가 좋아하는 동물!")
    @JsonProperty(value = "custom_title")
    private String customTitle;

    @NotBlank(message = NEOErrorCode.ValidationMessage.BLANK_VALUE)
    @Schema(description = "위에서 정의한 제목에 매칭되는 커스텀 내용", example = "고양이")
    @JsonProperty(value = "custom_context")
    private String customContext;

}

package com.neo.needeachother.users.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.neo.needeachother.users.enums.NEOGenderType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * @author 이승훈<br>
 * @since 23.08.21<br>
 * 팬의 정보 중, 모든 사람에게 공개되는 데이터에 대한 DTO
 */
@Getter
@ToString
@Builder
@Schema(description = "NEO 팬 유저의 공개 정보 객체")
public class NEOPublicFanInfoDto {

    @Schema(description = "NEO에서 사용하는 닉네임", example = "네오최고")
    @JsonProperty(value = "nickname", required = true)
    private String neoNickName;

    @Schema(description = "성별", example = "여성")
    @JsonProperty(value = "gender", required = true)
    private NEOGenderType gender;

}

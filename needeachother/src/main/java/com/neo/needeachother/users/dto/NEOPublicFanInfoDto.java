package com.neo.needeachother.users.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neo.needeachother.users.enums.NEOGenderType;
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
public class NEOPublicFanInfoDto {

    @JsonProperty(value = "nickname", required = true)
    private String neoNickName;

    @JsonProperty(required = true)
    private NEOGenderType gender;

}

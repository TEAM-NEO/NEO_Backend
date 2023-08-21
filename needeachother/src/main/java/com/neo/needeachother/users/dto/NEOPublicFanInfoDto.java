package com.neo.needeachother.users.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neo.needeachother.users.enums.NEOGenderType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class NEOPublicFanInfoDto {

    @JsonProperty(value = "nickname", required = true)
    private String neoNickName;

    @JsonProperty(required = true)
    private NEOGenderType gender;

}

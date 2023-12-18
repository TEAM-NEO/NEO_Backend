package com.neo.needeachother.auth.dto;

import com.neo.needeachother.users.enums.NEOGenderType;

import java.util.Map;

public abstract class NEOOAuth2UserInfoDTO {

    protected Map<String, Object> attributes;

    public NEOOAuth2UserInfoDTO(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    // 소셜 식별 값 : 구글 - "sub", 카카오 - "id", 네이버 - "id"
    public abstract String getId();

    public abstract String getEmail();

    public abstract NEOGenderType getGender();

    public abstract String getName();

    public abstract String getMobile();

}
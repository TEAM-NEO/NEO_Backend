package com.neo.needeachother.auth.dto;

import com.neo.needeachother.users.enums.NEOGenderType;

import java.util.Map;

public class NEOGoogleOAuth2UserInfoDTO extends NEOOAuth2UserInfoDTO{

    public NEOGoogleOAuth2UserInfoDTO(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public NEOGenderType getGender() {
        return NEOGenderType.NONE;
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getMobile() {
        return null;
    }
}

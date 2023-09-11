package com.neo.needeachother.auth.dto;

import com.neo.needeachother.users.enums.NEOGenderType;

import java.util.Map;

public class NEONaverOAuth2UserInfoDTO extends NEOOAuth2UserInfoDTO{

    public NEONaverOAuth2UserInfoDTO(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        Map<String, Object> response = getResponseMap();
        if (response == null) {
            return null;
        }
        return (String) response.get("id");
    }

    @Override
    public String getEmail() {
        Map<String, Object> response = getResponseMap();
        if (response == null) {
            return null;
        }
        return (String) response.get("email");
    }

    @Override
    public NEOGenderType getGender() {
        Map<String, Object> response = getResponseMap();
        if (response == null) {
            return null;
        }
        return NEOGenderType.convertFrom((String) response.get("gender"));
    }

    @Override
    public String getName() {
        Map<String, Object> response = getResponseMap();
        if (response == null) {
            return null;
        }
        return (String) response.get("name");
    }

    @Override
    public String getMobile() {
        Map<String, Object> response = getResponseMap();
        if (response == null) {
            return null;
        }
        return (String) response.get("mobile");
    }

    private Map<String, Object> getResponseMap() {
        return (Map<String, Object>) attributes.get("response");
    }
}

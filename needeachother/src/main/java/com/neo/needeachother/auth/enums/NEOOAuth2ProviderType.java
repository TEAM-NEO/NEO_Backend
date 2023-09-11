package com.neo.needeachother.auth.enums;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum NEOOAuth2ProviderType {
    NAVER("naver"),
    KAKAO("kakao"),
    GOOGLE("google");

    private final String registrationID;

    public static NEOOAuth2ProviderType ofRegistrationId(String registrationId){
        return Arrays.stream(NEOOAuth2ProviderType.values())
                .filter(provider -> provider.registrationID.equals(registrationId))
                .findAny()
                .orElseThrow();
    }
}

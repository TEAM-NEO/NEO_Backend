package com.neo.needeachother.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neo.needeachother.auth.enums.NEOOAuth2ProviderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NEOOAuth2SuccessResponse {

    @JsonProperty(value = "provider_type")
    private NEOOAuth2ProviderType providerType;

    private String msg;

    @JsonProperty(value = "access_token")
    private String accessToken;

    @JsonProperty(value = "refresh_token")
    private String refreshToken;
}

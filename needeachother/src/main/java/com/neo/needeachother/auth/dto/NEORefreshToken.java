package com.neo.needeachother.auth.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class NEORefreshToken {

    private String refreshToken;
    private String email;

    public NEORefreshToken(final String refreshToken, final String email) {
        this.refreshToken = refreshToken;
        this.email = email;
    }

    public static NEORefreshToken of(String email){
        return new NEORefreshToken(UUID.randomUUID().toString(), email);
    }
}

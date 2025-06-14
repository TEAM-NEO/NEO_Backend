package com.neo.needeachother.member.domain.neouser;

import com.neo.needeachother.auth.enums.NEOOAuth2ProviderType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;


@Embeddable
@AllArgsConstructor
@EqualsAndHashCode
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NEOUserAuthenticationProfile {
    /* 추후 폼 로그인을 위해 보류, OAuth2 가입시 email 값이 userID 에도 매핑 */
    @Column(name = "neo_id")
    private String neoID;

    @Column(name = "social_id")
    private String socialID;

    /* 추후 폼 로그인을 위해 보류, OAuth2 가입시 랜덤 값 생성 */
    @Column(name = "password")
    private String password;

    @Column(name = "provider_type")
    @Enumerated(value = EnumType.STRING)
    private NEOOAuth2ProviderType providerType;

    public static NEOUserAuthenticationProfile of(String neoID, String socialID, String passWord, String oAuth2ProviderType) {
        return NEOUserAuthenticationProfile.builder()
                .neoID(neoID)
                .socialID(socialID)
                .password(passWord)
                .providerType(NEOOAuth2ProviderType.ofRegistrationId(oAuth2ProviderType))
                .build();
    }
}

package com.neo.needeachother.member.domain.neoseeker;

import com.neo.needeachother.users.converter.NEOGenderTypeConverter;
import com.neo.needeachother.users.enums.NEOGenderType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NEOSeekerBasicProfile {

    @Column(name = "seeker_name")
    private String seekerRealName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "neo_nick_name", unique = true)
    private String neoNickName;

    @Column(name = "gender")
    @Convert(converter = NEOGenderTypeConverter.class)
    private NEOGenderType gender;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    // 도메인 로직: 프로필 정보 변경
    protected NEOSeekerBasicProfile changeNickName(String newNickName) {
        return new NEOSeekerBasicProfile(this.seekerRealName, this.email, this.phoneNumber,
                newNickName, this.gender, this.birthDate);
    }

    public static NEOSeekerBasicProfile of(String seekerRealName, String email, String phoneNumber, NEOGenderType gender, LocalDate birthDate, String neoNickName) {
        return NEOSeekerBasicProfile.builder()
                .seekerRealName(seekerRealName)
                .birthDate(birthDate)
                .email(email)
                .phoneNumber(phoneNumber)
                .gender(gender)
                .neoNickName(neoNickName)
                .build();
    }
}
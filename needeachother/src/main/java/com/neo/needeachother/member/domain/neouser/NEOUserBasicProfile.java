package com.neo.needeachother.member.domain.neouser;

import com.neo.needeachother.users.converter.NEOGenderTypeConverter;
import com.neo.needeachother.users.enums.NEOGenderType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Embeddable
@EqualsAndHashCode
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NEOUserBasicProfile {

    @Column(name = "user_name")
    private String userRealName;

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

    public static NEOUserBasicProfile of(String userRealName, String email, String phoneNumber, String genderCode, LocalDate birthDate, String neoNickName) {
        return NEOUserBasicProfile.builder()
                .userRealName(userRealName)
                .birthDate(birthDate)
                .email(email)
                .phoneNumber(phoneNumber)
                .gender(NEOGenderType.convertFrom(genderCode))
                .neoNickName(neoNickName)
                .build();
    }
}
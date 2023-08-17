package com.neo.needeachother.users.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.neo.needeachother.common.enums.NEOErrorCode;
import com.neo.needeachother.users.exception.NEOUserExpectedException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


@Getter
@RequiredArgsConstructor
public enum NEOGenderType {
    MALE("M", "남성"),
    FEMALE("F", "여성");

    private final String genderCode;
    private final String korGenderCode;

    public static NEOGenderType convertGenderCodeToEnum(String genderCode) {
        return Arrays.stream(NEOGenderType.values())
                .filter(genderType -> genderType.genderCode.equals(genderCode))
                .findAny()
                .orElseThrow();
    }

    @JsonCreator
    public static NEOGenderType convertFrom(String requestFieldData) {
        return NEOGenderType.valueOf(
                Stream.of(NEOGenderType.MALE, NEOGenderType.FEMALE)
                        .map(NEOGenderType::convertGenderStringList)
                        .filter(strings -> strings.contains(requestFieldData))
                        .findAny()
                        .orElseThrow(() -> new NEOUserExpectedException(NEOErrorCode.INVALID_FORMAT_GENDER,
                                "에러 대상 : " + requestFieldData,
                                "새로운 스타 정보 생성에 실패했습니다."))
                        .get(0));
    }

    public static List<String> convertGenderStringList(NEOGenderType genderType) {
        return List.of(genderType.name(), genderType.genderCode, genderType.korGenderCode);
    }
}

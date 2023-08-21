package com.neo.needeachother.users.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.neo.needeachother.common.enums.NEODomainType;
import com.neo.needeachother.common.enums.NEOErrorCode;
import com.neo.needeachother.common.exception.NEOExpectedException;
import com.neo.needeachother.users.exception.NEOUserExpectedException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;


@Getter
@Slf4j
@RequiredArgsConstructor
public enum NEOGenderType {
    MALE("M", "남성"),
    FEMALE("F", "여성"),
    NONE(null, null);

    private final String genderCode;
    private final String korGenderCode;

    public static NEOGenderType convertGenderCodeToEnum(String genderCode) {
        return Arrays.stream(NEOGenderType.values())
                .filter(genderType -> genderType.genderCode.equals(genderCode))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("Enum 타입으로 Convert 하기에 적합하지 않습니다."));
    }

    @JsonCreator
    public static NEOGenderType convertFrom(String requestFieldData) {
        return NEOGenderType.valueOf(
                Stream.of(NEOGenderType.MALE, NEOGenderType.FEMALE)
                        .map(NEOGenderType::convertGenderStringList)
                        .filter(strings -> strings.contains(requestFieldData))
                        .findAny()
                        .orElseGet(() -> {
                            log.warn("잘못 요청한 성별 코드 : " + requestFieldData);
                            return List.of("NONE");
                        }).get(0));
    }

    @JsonValue
    public String getKorGenderCode() {
        return korGenderCode;
    }

    public static List<String> convertGenderStringList(NEOGenderType genderType) {
        return List.of(genderType.name(), genderType.genderCode, genderType.korGenderCode);
    }
}

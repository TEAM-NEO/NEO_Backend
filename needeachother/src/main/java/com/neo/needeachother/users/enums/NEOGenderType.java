package com.neo.needeachother.users.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.neo.needeachother.common.enums.NEODocumentAbleEnum;
import com.neo.needeachother.common.enums.NEOEqualCodeEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;


/**
 * @author 이승훈<br>
 * @since 23.08.21<br>
 * NEO의 성별 타입을 정의
 */
@Getter
@Slf4j
@RequiredArgsConstructor
public enum NEOGenderType implements NEOEqualCodeEnum {
    MALE("M", "남성", "male"),
    FEMALE("F", "여성", "female"),

    /* 미정의 에러 타입 */
    NONE("NONE", "미정의", "none");

    private final String genderCode;
    private final String korGenderCode;
    private final String lowerCaseName;

    /**
     * DTO <-> Entity Converter에서 사용되는 로직입니다. <br>
     * 오류가 난다면 Bad request가 아닌 Internal한 문제이므로 올바르지 않은 타입이 주입된다면 Exception을 발생시킵니다.
     * @param genderCode 성별 식별 코드
     * @return {@code NEOGenderType} NEO 성별 Enum 타입 객체
     */
    public static NEOGenderType convertGenderCodeToEnum(String genderCode) {
        return Arrays.stream(NEOGenderType.values())
                .filter(genderType -> genderType.genderCode.equals(genderCode))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("Enum 타입으로 Convert 하기에 적합하지 않습니다."));
    }

    /**
     * Request에서 Java 객체로 역직렬화 하는 과정에서 필드 문자열 데이터로부터 {@code NEOGenderType}으로 변환하는 로직 메소드입니다.<br>
     * {@code NEOGenderType}의 {@code name}, {@code genderCode}, {@code korGenderCode}에 해당하는 문자열 값 중 하나를 파라미터로 제공하면, <br>
     * 그와 매칭되는 {@code NEOGenderType}를 반환합니다.<br>
     * 매칭되는 타입이 없는 경우 {@code NEOGenderType.NONE}을 리턴하는 정적 팩토리 메소드 입니다.
     * @param requestFieldData 요청의 필드 문자열 데이터
     * @return {@code NEOGenderType} 매칭된 {@code NEOGenderType}
     */
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

    /**
     * 한국어로 서술된 성별 코드를 반환하는 메소드입니다.<br>
     * 기본적으로 Java 객체에서 JSON으로 직렬화 하는 과정에서 Enum 값을 해당 문자열 값으로 대체합니다.
     * @return {@code String} 한국어 성별 코드
     */
    @JsonValue
    public String getKorGenderCode() {
        return korGenderCode;
    }

    /**
     * {@code NEOGenderType}과 매칭되는 문자열 리스트를 반환합니다. <br>
     * @param genderType NEO의 성별 타입
     * @return {@code List<String>} 해당 성별 타입과 매칭되는 문자열 리스트
     */
    public static List<String> convertGenderStringList(NEOGenderType genderType) {
        return List.of(genderType.name(), genderType.genderCode, genderType.korGenderCode, genderType.lowerCaseName);
    }

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public String getDescription() {
        return this.korGenderCode;
    }

    @Override
    public String getEqualCode() {
        return convertGenderStringList(this).toString();
    }
}

package com.neo.needeachother.users.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.neo.needeachother.common.enums.NEODocumentAbleEnum;
import com.neo.needeachother.common.enums.NEOEqualCodeEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author 이승훈<br>
 * @since 23.08.21<br>
 * NEO에서 정의하는 스타의 상세 분류 타입입니다. 해당 타입을 토대로 스타 유형을 구분합니다.
 */
@Slf4j
@Getter
@RequiredArgsConstructor
public enum NEOStarDetailClassification implements NEOEqualCodeEnum {

    /* 연예인 */
    ACTOR("AC", NEOStarClassification.CELEBRITY, "배우", null, null),
    SINGER("SI", NEOStarClassification.CELEBRITY, "가수", null, null),
    SPORT_STAR("SP", NEOStarClassification.CELEBRITY, "스포츠스타", null, null),
    COMEDIAN("CO", NEOStarClassification.CELEBRITY, "코미디언", null, null),
    CELEBRITY("CE", NEOStarClassification.CELEBRITY, "연예인", null, null),

    /* 인터넷 방송인 */
    YOUTUBER("YO", NEOStarClassification.INTERNET_BROADCASTER, "유튜버", Pattern.compile(".*youtube\\.com.*"), null),
    TWITCH_STREAMER("TW", NEOStarClassification.INTERNET_BROADCASTER, "스트리머", Pattern.compile(".*twitch\\.tv.*"), null),
    AFREECA_BJ("AF", NEOStarClassification.INTERNET_BROADCASTER, "BJ", Pattern.compile(".*afreeca\\.tv.*"), null),

    /* SNS 스타 */
    SNS_INFLUENCER("SN", NEOStarClassification.SNS_STAR, "SNS 인플루언서", Pattern.compile(".*(?:instagram\\.com|tiktok\\.com|facebook\\.com).*"), null),

    /* 미정의 */
    NONE("NONE", null, "미정의", null, null);

    /* 실제 데이터베이스에 저장되는 스타 분류 코드 */
    private final String classificationCode;

    /* 스타 직업군 대분류 */
    private final NEOStarClassification starClassificationGroup;

    /* 스타 직업군 한국어 분류 */
    private final String korStarClassification;

    /* URL 매칭을 위한 정규표현식 패턴 정의 */
    private final Pattern urlRegexPattern;

    /* 스타 직업군 별 대표 이미지 링크 */
    private final String representMarkImgUrl;


    /* static field & method */
    public static HashMap<NEOStarDetailClassification, HashSet<String>> classificationStringDict;

    /**
     * {@code NEOStarDetailClassification}와 매칭되는 문자열 리스트를 집합의 형태로 만들어 주는 정적 팩토리 메소드.<br>
     * {@code classificationStringDict}를 static 하게 구성하기 위해 사용합니다.
     * @param classification 스타 상세 분류 타입
     * @return {@code HashSet<String>} 해당 스타 상세 분류 타입과 매칭되는 문자열 집합
     */
    public static HashSet<String> getClassificationStringList(NEOStarDetailClassification classification) {
        return new HashSet<>(
                List.of(classification.name(),
                        classification.korStarClassification,
                        classification.classificationCode));
    }

    /* classificationStringDict 초기화 */
    static {
        classificationStringDict = new HashMap<>();
        Arrays.stream(NEOStarDetailClassification.values())
                .forEach(classification -> classificationStringDict.put(classification, getClassificationStringList(classification)));
    }

    /* JsonCreator for request convert to enum */
    @JsonCreator
    public static NEOStarDetailClassification fromString(String classificationCode) {
        return Arrays.stream(NEOStarDetailClassification.values())
                .filter(classification -> classificationStringDict.get(classification).contains(classificationCode))
                .findAny()
                .orElseGet(() -> {
                    log.warn("잘못 요청된 스타 구분자 코드 : " + classificationCode);
                    return NONE;
                });
    }

    /* JsonValue for response field convert to String */
    @JsonValue
    public String getKorClassificationCode() {
        return korStarClassification;
    }

    /**
     * {@code fromString} 메소드와 같은 로직으로, List <-> List 변환 로직을 갖습니다.<br>
     * 차이점은, {@code NEOStarDetailClassification.NONE}에 대해 필터링을 거쳐 {@code List<NEOStarDetailClassification>}에 포함되지 않도록 합니다.
     * @param classStringList 스타 상세 분류 문자열 리스트
     * @return {@code List<NEOStarDetailClassification>} 스타 상세 분류 타입 리스트
     */
    public static List<NEOStarDetailClassification> fromStringList(List<String> classStringList) {
        return classStringList.stream()
                .map(classString -> Arrays.stream(NEOStarDetailClassification.values())
                        .filter(classification -> classificationStringDict.get(classification).contains(classString))
                        .findAny()
                        .orElse(NONE)
                ).filter(enums -> !enums.equals(NONE))
                .collect(Collectors.toList());
    }

    /**
     * DTO <-> Entity 변환 과정에서 사용되는 메소드입니다.<br>
     * db에 저장된 코드 데이터를 기반으로 {@code NEOStarDetailClassification}로 변환합니다.
     * @param code dt data
     * @return {@code NEOStarDetailClassification} 스타 상세 분류 타입
     */
    public static NEOStarDetailClassification convertClassificationCodeToEnum(String code) {
        return Arrays.stream(NEOStarDetailClassification.values())
                .filter(classification -> classification.classificationCode.equals(code))
                .findAny()
                .orElseThrow();
    }

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public String getDescription() {
        return this.korStarClassification;
    }

    @Override
    public String getEqualCode() {
        return getClassificationStringList(this).toString();
    }
}

package com.neo.needeachother.users.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.neo.needeachother.common.enums.NEODomainType;
import com.neo.needeachother.common.enums.NEOErrorCode;
import com.neo.needeachother.common.exception.NEOExpectedException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum NEOStarDetailClassification {

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

    public static HashSet<String> getClassificationStringList(NEOStarDetailClassification classification) {
        return new HashSet<>(
                List.of(classification.name(),
                        classification.korStarClassification,
                        classification.classificationCode));
    }

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
                .orElseThrow(() -> new NEOExpectedException(NEODomainType.COMMON, NEOErrorCode.INVALID_FORMAT_STAR_CLASSIFICATION,
                        "에러 원인 : " + classificationCode,
                        NEOErrorCode.INVALID_FORMAT_STAR_CLASSIFICATION.getErrorDescription()));
    }

    @JsonValue
    public String getClassificationCode() {
        return korStarClassification;
    }

    public static List<NEOStarDetailClassification> fromStringList(List<String> classStringList) {
        return classStringList.stream()
                .map(classString -> Arrays.stream(NEOStarDetailClassification.values())
                        .filter(classification -> classificationStringDict.get(classification).contains(classString))
                        .findAny()
                        .orElse(NONE)
                ).filter(enums -> !enums.equals(NONE))
                .collect(Collectors.toList());
    }

    public static NEOStarDetailClassification convertClassificationCodeToEnum(String code) {
        return Arrays.stream(NEOStarDetailClassification.values())
                .filter(classification -> classification.classificationCode.equals(code))
                .findAny()
                .orElseThrow();
    }

}

package com.neo.needeachother.users.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neo.needeachother.users.enums.NEOGenderType;
import com.neo.needeachother.users.enums.NEOStarDetailClassification;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author 이승훈<br>
 * @since 23.08.21<br>
 * 스타 정보중에 모든 유저에게 공개되는 데이터에 대한 DTO
 */
@Getter
@Setter
@Builder
@ToString
public class NEOPublicStarInfoDto {

    @JsonProperty(value = "star_nickname", required = true)
    private String starNickName;

    @JsonProperty(required = true)
    private NEOGenderType gender;

    @Builder.Default
    @JsonProperty(value = "star_classification_list", required = true)
    private HashSet<NEOStarDetailClassification> starClassificationSet = new HashSet<>();

    @Builder.Default
    @JsonProperty("submitted_url")
    private List<String> submittedUrl = new ArrayList<>();

    private String introduction;

    @JsonProperty("custom_introduction_list")
    private List<NEOCustomStarInformation> customIntroductionList;

}

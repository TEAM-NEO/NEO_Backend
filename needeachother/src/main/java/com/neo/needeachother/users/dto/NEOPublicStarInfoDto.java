package com.neo.needeachother.users.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neo.needeachother.users.enums.NEOGenderType;
import com.neo.needeachother.users.enums.NEOStarDetailClassification;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "NEO 스타 유저의 공개 정보 객체")
public class NEOPublicStarInfoDto {

    @Schema(description = "스타의 실제 활동명", example = "아이유")
    @JsonProperty(value = "star_nickname", required = true)
    private String starNickName;

    @Schema(description = "성별", example = "여성")
    @JsonProperty(value = "gender", required = true)
    private NEOGenderType gender;

    @Schema(description = "스타 구분자 리스트", example = "[\"유튜버\", \"가수\", \"배우\"]")
    @Builder.Default
    @JsonProperty(value = "star_classification_list", required = true)
    private HashSet<NEOStarDetailClassification> starClassificationSet = new HashSet<>();

    @Schema(description = "스타와 관계된 링크", example = "[\"www.youtube.com/@seanhong2000\", \"www.facebook.com/free_minkya\"]")
    @Builder.Default
    @JsonProperty(value = "submitted_url")
    private List<String> submittedUrl = new ArrayList<>();

    @Schema(description = "스타 한 줄 소개글", example = "안냐세요 찬호에요")
    @JsonProperty(value = "introduction")
    private String introduction;

    @Schema(description = "스타의 커스텀 자기소개 리스트", example = "[{\"custom_title\" : \"MBTI\", \"custom_context\" : \"ISTJ\"}]")
    @JsonProperty(value = "custom_introduction_list")
    private List<NEOStarWikiInformationDTO> customIntroductionList;

}

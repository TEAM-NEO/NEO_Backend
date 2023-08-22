package com.neo.needeachother.users.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neo.needeachother.users.enums.NEOStarDetailClassification;
import lombok.*;
import java.util.HashSet;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NEOChangeableInfoDto {

    @JsonProperty(value = "star_nickname")
    private String starNickName;

    @JsonProperty(value = "nickname")
    private String neoNickName;

    @JsonProperty(value = "star_classification_list")
    private HashSet<NEOStarDetailClassification> starClassificationSet;

    @JsonProperty("submitted_url")
    private List<String> submittedUrl;

    private String introduction;

    @JsonProperty("custom_introduction_list")
    private List<NEOCustomStarInformation> customIntroductionList;

}

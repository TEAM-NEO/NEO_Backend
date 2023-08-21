package com.neo.needeachother.users.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neo.needeachother.common.enums.NEOErrorCode;
import com.neo.needeachother.users.document.NEOStarInfoDocument;
import com.neo.needeachother.users.enums.NEOGenderType;
import com.neo.needeachother.users.enums.NEOStarDetailClassification;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class NEOPublicStarInfoDto {

    @JsonProperty(value = "star_nickname", required = true)
    private String starNickName;

    @JsonProperty(required = true)
    private NEOGenderType gender;

    @JsonProperty(value = "star_classification_list", required = true)
    private HashSet<NEOStarDetailClassification> starClassificationSet = new HashSet<>();

    @JsonProperty("submitted_url")
    private List<String> submittedUrl = new ArrayList<>();

    private String introduction;

    @JsonProperty("custom_introduction_list")
    private List<NEOCustomStarInformation> customIntroductionList;

    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NEOCustomStarInformation {

        @NotBlank(message = NEOErrorCode.ValidationMessage.BLANK_VALUE)
        @JsonProperty("custom_title")
        private String customTitle;

        @NotBlank(message = NEOErrorCode.ValidationMessage.BLANK_VALUE)
        @JsonProperty("custom_context")
        private String customContext;

        public NEOStarInfoDocument.NEOStarCustomInformation convertToDocumentFormat(){
            return new NEOStarInfoDocument.NEOStarCustomInformation(null, this.getCustomTitle(), this.getCustomContext());
        }
    }
}

package com.neo.needeachother.starpage.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neo.needeachother.common.enums.NEOErrorCode;
import com.neo.needeachother.starpage.domain.SNSLine;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateStarPageRequest {

    @NotBlank(message = NEOErrorCode.ValidationMessage.BLANK_VALUE)
    @JsonProperty(value = "star_nickname", required = true)
    private String starNickName;

    @NotBlank(message = NEOErrorCode.ValidationMessage.BLANK_VALUE)
    @JsonProperty(value = "email", required = true)
    private String email;

    @NotBlank(message = NEOErrorCode.ValidationMessage.BLANK_VALUE)
    @JsonProperty(value = "star_type", required = true)
    Set<String> starTypeSet;

    @NotBlank(message = NEOErrorCode.ValidationMessage.BLANK_VALUE)
    @JsonProperty(value = "sns_profile", required = true)
    List<SNSProfile> snsProfiles;

    @NotBlank(message = NEOErrorCode.ValidationMessage.BLANK_VALUE)
    @JsonProperty(value = "starpage_introduce", required = true)
    String starPageIntroduce;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SNSProfile {

        @NotBlank(message = NEOErrorCode.ValidationMessage.BLANK_VALUE)
        @JsonProperty(value = "sns_type", required = true)
        String snsTypeName;

        @NotBlank(message = NEOErrorCode.ValidationMessage.BLANK_VALUE)
        @JsonProperty(value = "sns_url", required = true)
        String url;
    }
}

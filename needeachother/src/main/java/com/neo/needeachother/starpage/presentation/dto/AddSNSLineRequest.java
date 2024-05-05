package com.neo.needeachother.starpage.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neo.needeachother.common.enums.NEOErrorCode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddSNSLineRequest {

    @NotBlank(message = NEOErrorCode.ValidationMessage.BLANK_VALUE)
    @JsonProperty(value = "star_page_id", required = true)
    private String starPageId;

    @NotBlank(message = NEOErrorCode.ValidationMessage.BLANK_VALUE)
    @Email(message = NEOErrorCode.ValidationMessage.INVALID_FORMAT_EMAIL)
    @JsonProperty(value = "email", required = true)
    private String email;

    @NotBlank(message = NEOErrorCode.ValidationMessage.BLANK_VALUE)
    @JsonProperty(value = "sns_name", required = true)
    private String snsName;

    @NotBlank(message = NEOErrorCode.ValidationMessage.BLANK_VALUE)
    @JsonProperty(value = "sns_url", required = true)
    private String snsUrl;
}

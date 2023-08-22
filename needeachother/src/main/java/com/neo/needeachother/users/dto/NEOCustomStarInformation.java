package com.neo.needeachother.users.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neo.needeachother.common.enums.NEOErrorCode;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NEOCustomStarInformation {

    @NotBlank(message = NEOErrorCode.ValidationMessage.BLANK_VALUE)
    @JsonProperty("custom_title")
    private String customTitle;

    @NotBlank(message = NEOErrorCode.ValidationMessage.BLANK_VALUE)
    @JsonProperty("custom_context")
    private String customContext;

}

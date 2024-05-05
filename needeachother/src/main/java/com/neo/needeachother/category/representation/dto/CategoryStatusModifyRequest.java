package com.neo.needeachother.category.representation.dto;

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
public class CategoryStatusModifyRequest {

    @NotBlank(message = NEOErrorCode.ValidationMessage.BLANK_VALUE)
    @Email(message = NEOErrorCode.ValidationMessage.INVALID_FORMAT_EMAIL)
    @JsonProperty(value = "star_page_id", required = true)
    private String email;

    @NotBlank(message = NEOErrorCode.ValidationMessage.BLANK_VALUE)
    @JsonProperty(value = "category_id", required = true)
    private String categoryId;

}

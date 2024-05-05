package com.neo.needeachother.category.representation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neo.needeachother.common.enums.NEOErrorCode;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryCreationRequest {

    @NotBlank(message = NEOErrorCode.ValidationMessage.BLANK_VALUE)
    @JsonProperty(value = "star_page_id", required = true)
    private String starPageId;

    @NotBlank(message = NEOErrorCode.ValidationMessage.BLANK_VALUE)
    @JsonProperty(value = "category_title", required = true)
    private String categoryTitle;

    @NotBlank(message = NEOErrorCode.ValidationMessage.BLANK_VALUE)
    @JsonProperty(value = "category_type", required = true)
    private String categoryType;

    @NotBlank(message = NEOErrorCode.ValidationMessage.BLANK_VALUE)
    @JsonProperty(value = "is_write_host_only", required = true)
    private boolean isWriteHostOnly;

    @NotBlank(message = NEOErrorCode.ValidationMessage.BLANK_VALUE)
    @JsonProperty(value = "is_comment_writeable", required = true)
    private boolean isCommentWriteAble;

    @NotBlank(message = NEOErrorCode.ValidationMessage.BLANK_VALUE)
    @JsonProperty(value = "is_using_rating_filter", required = true)
    private boolean isUsingRatingFilter;

    @NotBlank(message = NEOErrorCode.ValidationMessage.BLANK_VALUE)
    @JsonProperty(value = "filtering_rate", required = true)
    private int filteringRate;
}

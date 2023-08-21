package com.neo.needeachother.users.dto;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.neo.needeachother.common.enums.NEOErrorCode;
import com.neo.needeachother.users.enums.NEOGenderType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
@Builder
@JsonFilter("NEOInfoDtoJsonFilter")
@Schema(description = "OAuth2.0을 통한 회원가입 이후의, 팬의 추가 정보를 입력하기 위한 API의 Request DTO입니다.")
public class NEOFanInfoDto {

    /* OAuth2.0으로부터 받아온 사용자의 ID, OAuth를 도입하더라도 삭제되지 않고, 회원가입시 생성된 엔티티를 찾아내기 위한 식별용으로 보존. */
    @NotBlank(message = NEOErrorCode.ValidationMessage.BLANK_VALUE)
    @Size(min = 4, message = NEOErrorCode.ValidationMessage.MIN_INVALID_USER_ID)
    @Schema(description = "OAuth2.0으로부터 받아온 사용자의 ID", example = "free_minkya")
    @JsonProperty(value = "user_id", required = true)
    private String userID;

    /* OAuth2.0으로부터 받아온 사용자의 PW, OAuth 도입 이후 request에서 제거 예정 */
    @NotBlank(message = NEOErrorCode.ValidationMessage.BLANK_VALUE)
    @Size(min = 4, message = NEOErrorCode.ValidationMessage.MIN_INVALID_USER_PW)
    @Schema(description = "OAuth2.0으로부터 받아온 사용자의 PW, OAuth 도입 이후 request에서 제거 예정", example = "Aabc1234!")
    @JsonProperty(value = "user_pw", required = true)
    private String userPW;

    /* OAuth2.0으로부터 받아온 사용자의 이메일, OAuth 도입 이후 request에서 제거 예정 */
    @NotBlank(message = NEOErrorCode.ValidationMessage.BLANK_VALUE)
    @Email(message = NEOErrorCode.ValidationMessage.INVALID_FORMAT_EMAIL)
    @Schema(description = "OAuth2.0으로부터 받아온 사용자의 이메일, OAuth 도입 이후 request에서 제거 예정", example = "aaa@example.com")
    @JsonProperty(required = true)
    private String email;

    /* OAuth2.0으로부터 받아온 사용자의 사용자 이름, OAuth 도입 이후 request에서 제거 예정 */
    @NotBlank(message = NEOErrorCode.ValidationMessage.BLANK_VALUE)
    @Pattern(regexp = "^[가-힣]*$", message = NEOErrorCode.ValidationMessage.NOT_ONLY_KOR_USERNAME)
    @Size(min = 2, message = NEOErrorCode.ValidationMessage.MIN_INVALID_USERNAME)
    @Schema(description = "OAuth2.0으로부터 받아온 사용자의 이름, OAuth 도입 이후 request에서 제거 예정", example = "이승훈")
    @JsonProperty(value = "user_name", required = true)
    private String userName;

    /* OAuth2.0으로부터 받아온 사용자의 전화번호, OAuth 도입 이후 request에서 제거 예정 */
    @NotBlank(message = NEOErrorCode.ValidationMessage.BLANK_VALUE)
    @Pattern(regexp = "^(010|011|016|017|018|019)-?(?:\\d{3,4})-?\\d{4}$",
            message = NEOErrorCode.ValidationMessage.INVALID_FORMAT_PHONE_NUMBER)
    @Schema(description = "핸드폰 번호", example = "010-0000-0000")
    @JsonProperty(value = "phone_number", required = true)
    private String phoneNumber;

    /* 네오에서 사용할 닉네임 */
    @NotBlank(message = NEOErrorCode.ValidationMessage.BLANK_VALUE)
    @Schema(description = "네오에서 사용하게 될 닉네임으로, 다른 스타페이지에서 활동할 때 해당 닉네임을 사용할 수 있습니다.", example = "네오123")
    @JsonProperty(value = "nickname", required = true)
    private String neoNickName;

    @NotBlank(message = NEOErrorCode.ValidationMessage.BLANK_VALUE)
    @Schema(description = "성별", example = "M")
    @JsonProperty(required = true)
    private NEOGenderType gender;

    @NotBlank(message = NEOErrorCode.ValidationMessage.BLANK_VALUE)
    @Schema(description = "최애의 아이디", example = "free_minkya")
    @JsonProperty(value = "favorite_star_id", required = true)
    private String favoriteStarID;

}

package com.neo.needeachother.users.dto;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.neo.needeachother.common.enums.NEOErrorCode;
import com.neo.needeachother.users.enums.NEOGenderType;
import com.neo.needeachother.users.enums.NEOStarDetailClassification;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author 이승훈<br>
 * @since 23.08.21<br>
 * 스타 유저의 정보를 저장하는 DTO입니다.
 */
@Setter
@Getter
@ToString
@Builder
@JsonFilter("NEOInfoDtoJsonFilter")
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "OAuth2.0을 통한 회원가입 이후의, 스타의 추가 정보를 입력하기 위한 API의 Request DTO입니다.")
public class NEOStarInfoDto {

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
    @JsonProperty(value = "email", required = true)
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

    /* 스타의 실제 활동명 */
    @NotBlank(message = NEOErrorCode.ValidationMessage.BLANK_VALUE)
    @Schema(description = "스타가 현실에서 실제로 활동하고 있는 활동명을 입력합니다. 이모티콘 혹은 활동명의 변조는 추후 인증 과정에서 불이익을 받을 수 있습니다.", example = "아이유")
    @JsonProperty(value = "star_nickname", required = true)
    private String starNickName;

    // @NotBlank(message = NEOErrorCode.ValidationMessage.BLANK_VALUE)
    @Schema(description = "성별", example = "M")
    @JsonProperty(value = "gender", required = true)
    private NEOGenderType gender;

    /* 스타 구분, 중복 선택 가능 */
    @NotEmpty(message = NEOErrorCode.ValidationMessage.BLANK_VALUE)
    @Schema(description = """
            어떤 분야의 스타인지 구별할 수 있는 구분자입니다.\s
            여러 분야에서 일할 수 있으므로, 문자열 리스트의 형태로 값을 전달하면 됩니다.
            문자열은 한국어 직업군, 대문자 영어 직업군, 분류 코드 모두 수용 가능합니다.""", example = "[\"YOUTUBER\", \"가수\", \"SN\"]")
    @JsonProperty(value = "star_classification_list", required = true)
    private HashSet<NEOStarDetailClassification> starClassificationSet = new HashSet<>();

    /* 제출한 url */
    @Schema(description = "제출한 URL로, 스타 구분에 맞는 URL을 식별합니다.", example = "[\"www.youtube.com/@seanhong2000\"]")
    @JsonProperty("submitted_url")
    private List<String> submittedUrl = new ArrayList<>();

    /* 소개글 */
    @Schema(description = "스타 페이지 및 검색에 노출될 간단한 소개줄입니다.", example = "안녕하세요, 가수 아이유입니다.")
    @JsonProperty(value = "introduction")
    private String introduction;

    /* 커스텀 정보 입력 */
    @Schema(description = "추가적으로 스타가 직접 입력하고 싶은 커스텀 정보를 입력합니다." +
            "정보 제목과 정보 내용을 포함하고 있는 리스트 쌍을 제출합니다.", example = "[{\"custom_title\" : \"MBTI\", \"custom_context\" : \"ISFJ\"}]")
    @JsonProperty("custom_introduction_list")
    private List<NEOCustomStarInformation> customIntroductionList;

}

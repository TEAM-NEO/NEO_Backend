package com.neo.needeachother.users.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neo.needeachother.users.enums.NEOStarDetailClassification;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.HashSet;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "NEO 유저의 개인 정보 변경 요청 객체")
public class NEOChangeableInfoDto {

    @Schema(description = "스타가 현실에서 실제로 활동하고 있는 활동명을 입력합니다. 이모티콘 혹은 활동명의 변조는 추후 인증 과정에서 불이익을 받을 수 있습니다.\n" +
            "팬은 수정되거나 추가되지 않습니다.", example = "아이유", nullable = true)
    @JsonProperty(value = "star_nickname")
    private String starNickName;

    @Schema(description = "네오에서 사용하게 될 닉네임으로, 다른 스타페이지에서 활동할 때 해당 닉네임을 사용할 수 있습니다.", example = "네오123", nullable = true)
    @JsonProperty(value = "nickname")
    private String neoNickName;

    @Schema(description = """
            어떤 분야의 스타인지 구별할 수 있는 구분자입니다.\s
            여러 분야에서 일할 수 있으므로, 문자열 리스트의 형태로 값을 전달하면 됩니다.
            문자열은 한국어 직업군, 대문자 영어 직업군, 분류 코드 모두 수용 가능합니다.\n
            JSON에서 빈 리스트의 형태로 값을 전달하면, 이전에 설정된 스타 구분이 모두 사라지게 됩니다.\n
            팬은 수정되거나 추가되지 않습니다.""", example = "[\"YOUTUBER\", \"가수\", \"SN\"]", nullable = true)
    @JsonProperty(value = "star_classification_list")
    private HashSet<NEOStarDetailClassification> starClassificationSet;

    @Schema(description = "제출한 URL로, 스타 구분에 맞는 URL을 식별합니다. \n" +
            "JSON에서 빈 리스트의 형태로 값을 전달하면, 이전에 설정된 스타 구분이 모두 사라지게 됩니다.\n" +
            "팬은 수정되거나 추가되지 않습니다.", example = "[www.youtube.com/@seanhong2000]", nullable = true)
    @JsonProperty("submitted_url")
    private List<String> submittedUrl;

    @Schema(description = "스타 페이지 및 검색에 노출될 간단한 소개줄입니다.\n" +
            "팬은 수정되거나 추가되지 않습니다.", example = "안녕하세요, 가수 아이유입니다.", nullable = true)
    private String introduction;

    @Schema(description = "추가적으로 스타가 직접 입력하고 싶은 커스텀 정보를 입력합니다." +
            "정보 제목과 정보 내용을 포함하고 있는 리스트 쌍을 제출합니다.\n" +
            "팬은 수정되거나 추가되지 않습니다.", example = "[{\"customTitle\" : \"MBTI\", \"customContext\" : \"ISFJ\"}]", nullable = true)
    @JsonProperty("custom_introduction_list")
    private List<NEOCustomStarInformation> customIntroductionList;

}

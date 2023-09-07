package com.neo.needeachother.users.mother;

import com.neo.needeachother.users.dto.NEOAdditionalStarInfoRequest;
import com.neo.needeachother.users.dto.NEOChangeableInfoDTO;
import com.neo.needeachother.users.dto.NEOStarWikiInformationDTO;
import com.neo.needeachother.users.dto.NEOUserInformationDTO;
import com.neo.needeachother.users.enums.NEOGenderType;
import com.neo.needeachother.users.enums.NEOStarDetailClassification;
import com.neo.needeachother.users.enums.NEOUserType;
import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Getter
public enum NEOStarTestObjectMother {
    STAR_CASE_1("free_minkya", "1234", "free_minkya@naver.com", "가가가",
            "010-1111-1111", "박보영네오", "박보영", NEOGenderType.FEMALE,
            new HashSet<>(List.of(NEOStarDetailClassification.ACTOR, NEOStarDetailClassification.COMEDIAN)),
            List.of("www.youtube.com/@free_minkya"), "안녕하세요, 박보영입니다.",
            List.of(NEOStarWikiInformationDTO.builder().customTitle("MBTI").customContext("ISTJ").build()));

    private final String userID;

    /* mapping object */
    private final NEOAdditionalStarInfoRequest createRequestFixture;
    private final NEOAdditionalStarInfoRequest.NEOAdditionalStarInfoRequestBuilder createRequestBuilder;
    private final NEOUserInformationDTO.NEOUserInformationDTOBuilder userInfoDTOBuilder;

    NEOStarTestObjectMother(String userID, String userPW, String email, String userName, String phoneNumber,
                            String neoNickName, String starNickName, NEOGenderType gender, HashSet<NEOStarDetailClassification> starClassificationSet,
                            List<String> submittedUrl, String introduction, List<NEOStarWikiInformationDTO> customWikiList) {

        this.userID = userID;
        this.createRequestBuilder = NEOAdditionalStarInfoRequest.builder()
                .userID(userID)
                .userPW(userPW)
                .userName(userName)
                .email(email)
                .phoneNumber(phoneNumber)
                .neoNickName(neoNickName)
                .starNickName(starNickName)
                .gender(gender)
                .starClassificationSet(starClassificationSet)
                .submittedUrl(submittedUrl)
                .introduction(introduction)
                .customWikiList(customWikiList);

        this.createRequestFixture = this.createRequestBuilder.build();

        this.userInfoDTOBuilder = NEOUserInformationDTO.builder()
                .userType(NEOUserType.STAR)
                .hasWiki(true)
                .isPrivate(true)
                .userID(userID)
                .userPW(userPW)
                .email(email)
                .userName(userName)
                .phoneNumber(phoneNumber)
                .neoNickName(neoNickName)
                .starNickName(starNickName)
                .gender(gender)
                .starClassificationSet(starClassificationSet)
                .submittedUrl(submittedUrl)
                .introduction(introduction)
                .customWikiList(customWikiList);
    }

    public NEOUserInformationDTO getCreateResponseFixture() {
        return userInfoDTOBuilder.build();
    }

    public NEOUserInformationDTO getUserInfoResponseFixture() {
        return userInfoDTOBuilder.build();
    }

    public NEOUserInformationDTO getUserInfoResponseFixtureWithoutPrivacy() {
        return getUserInfoResponseFixture().toBuilder().isPrivate(false).build();
    }

    public NEOUserInformationDTO getUserInfoResponseFixtureWithoutWiki() {
        return getUserInfoResponseFixture().toBuilder()
                .hasWiki(false)
                .build();
    }

    public NEOUserInformationDTO getUserInfoResponseFixtureWithoutPrivacyAndWiki() {
        return getUserInfoResponseFixture().toBuilder()
                .isPrivate(false)
                .hasWiki(false)
                .build();
    }

    public NEOUserInformationDTO getChangeInfoResponseFixture(NEOChangeableInfoDTO changeableInfoDTO) {
        NEOUserInformationDTO.NEOUserInformationDTOBuilder clonedBuilder = getUserInfoResponseFixture().toBuilder();
        Optional.ofNullable(changeableInfoDTO.getStarNickName()).ifPresent(clonedBuilder::starNickName);
        Optional.ofNullable(changeableInfoDTO.getNeoNickName()).ifPresent(clonedBuilder::neoNickName);
        Optional.ofNullable(changeableInfoDTO.getIntroduction()).ifPresent(clonedBuilder::introduction);
        Optional.ofNullable(changeableInfoDTO.getStarClassificationSet()).ifPresent(clonedBuilder::starClassificationSet);
        Optional.ofNullable(changeableInfoDTO.getCustomWikiList()).ifPresent(clonedBuilder::customWikiList);
        Optional.ofNullable(changeableInfoDTO.getSubmittedUrl()).ifPresent(clonedBuilder::submittedUrl);
        return clonedBuilder.build();
    }

}

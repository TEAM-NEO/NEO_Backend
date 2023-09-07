package com.neo.needeachother.users.mother;

import com.neo.needeachother.users.dto.NEOAdditionalFanInfoRequest;
import com.neo.needeachother.users.dto.NEOChangeableInfoDTO;
import com.neo.needeachother.users.dto.NEOUserInformationDTO;
import com.neo.needeachother.users.enums.NEOGenderType;
import com.neo.needeachother.users.enums.NEOUserType;
import lombok.Getter;

import java.util.Optional;

@Getter
public enum NEOFanTestObjectMother {
    FAN_CASE_1("neofan1", "1234", "neofan1@naver.com", "이승훈", "010-1111-1112",
            "네오박보영팬", NEOGenderType.MALE, NEOStarTestObjectMother.STAR_CASE_1.getUserID());

    private final String userID;

    /* mapping object */
    private final NEOAdditionalFanInfoRequest createRequestFixture;
    private final NEOAdditionalFanInfoRequest.NEOAdditionalFanInfoRequestBuilder createRequestBuilder;
    private final NEOUserInformationDTO.NEOUserInformationDTOBuilder userInfoDTOBuilder;

    NEOFanTestObjectMother(String userID, String userPW, String email, String userName, String phoneNumber,
                           String neoNickName, NEOGenderType gender, String favoriteStarID) {

        this.userID = userID;
        this.createRequestBuilder = NEOAdditionalFanInfoRequest.builder()
                .userID(userID)
                .userPW(userPW)
                .userName(userName)
                .email(email)
                .phoneNumber(phoneNumber)
                .neoNickName(neoNickName)
                .gender(gender)
                .favoriteStarID(favoriteStarID);

        this.createRequestFixture = this.createRequestBuilder.build();

        this.userInfoDTOBuilder = NEOUserInformationDTO.builder()
                .userType(NEOUserType.FAN)
                .hasWiki(false)
                .isPrivate(true)
                .userID(userID)
                .userPW(userPW)
                .email(email)
                .userName(userName)
                .phoneNumber(phoneNumber)
                .neoNickName(neoNickName)
                .gender(gender);
    }

    public NEOUserInformationDTO getCreateResponseFixture(){
        return userInfoDTOBuilder.build();
    }

    public NEOUserInformationDTO getUserInfoResponseFixture(){
        return userInfoDTOBuilder.build();
    }

    public NEOUserInformationDTO getUserInfoResponseFixtureWithoutPrivacy(){
        return getUserInfoResponseFixture().toBuilder().isPrivate(false).build();
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

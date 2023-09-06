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
        return userInfoDTOBuilder.isPrivate(false).build();
    }

    public NEOUserInformationDTO getChangeInfoResponseFixture(NEOChangeableInfoDTO changeableInfoDTO) {
        Optional.ofNullable(changeableInfoDTO.getStarNickName()).ifPresent(userInfoDTOBuilder::starNickName);
        Optional.ofNullable(changeableInfoDTO.getNeoNickName()).ifPresent(userInfoDTOBuilder::neoNickName);
        Optional.ofNullable(changeableInfoDTO.getIntroduction()).ifPresent(userInfoDTOBuilder::introduction);
        Optional.ofNullable(changeableInfoDTO.getStarClassificationSet()).ifPresent(userInfoDTOBuilder::starClassificationSet);
        Optional.ofNullable(changeableInfoDTO.getCustomWikiList()).ifPresent(userInfoDTOBuilder::customWikiList);
        Optional.ofNullable(changeableInfoDTO.getSubmittedUrl()).ifPresent(userInfoDTOBuilder::submittedUrl);
        return userInfoDTOBuilder.build();
    }
}

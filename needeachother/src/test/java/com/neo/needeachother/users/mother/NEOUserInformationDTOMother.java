package com.neo.needeachother.users.mother;

import com.neo.needeachother.users.dto.NEOAdditionalStarInfoRequest;
import com.neo.needeachother.users.dto.NEOStarWikiInformationDTO;
import com.neo.needeachother.users.dto.NEOUserInformationDTO;
import com.neo.needeachother.users.enums.NEOGenderType;
import com.neo.needeachother.users.enums.NEOStarDetailClassification;
import com.neo.needeachother.users.enums.NEOUserType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;

public enum NEOUserInformationDTOMother {
    STAR_USER_INFORMATION_1("free_minkya", "1234", "free_minkya@naver.com", "가가가",
            "010-1111-1111", "박보영네오", "박보영", NEOGenderType.FEMALE,
            new HashSet<>(List.of(NEOStarDetailClassification.ACTOR, NEOStarDetailClassification.COMEDIAN)),
            List.of("www.youtube.com/@free_minkya"), "안녕하세요, 박보영입니다.",
            List.of(NEOStarWikiInformationDTO.builder().customTitle("MBTI").customContext("ISTJ").build()), NEOUserType.STAR);

    private final NEOUserInformationDTO.NEOUserInformationDTOBuilder userInformationBuilder;

    NEOUserInformationDTOMother(String userID, String userPW, String email, String userName, String phoneNumber,
                                String neoNickName, String starNickName, NEOGenderType gender, HashSet<NEOStarDetailClassification> starClassificationSet,
                                List<String> submittedUrl, String introduction, List<NEOStarWikiInformationDTO> customWikiList, NEOUserType userType) {

        this.userInformationBuilder = NEOUserInformationDTO.builder()
                .userType(userType)
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

    public NEOUserInformationDTO getUserInformationFixture(){
        return this.userInformationBuilder.build();
    }

    public NEOUserInformationDTO.NEOUserInformationDTOBuilder getUserInformationBuilder(){
        return this.getUserInformationFixture().toBuilder();
    }

    public NEOUserInformationDTO getUserInformationFixture(NEOAdditionalStarInfoRequestMother additionalStarInfoRequestMother) {
        if (additionalStarInfoRequestMother.equals(NEOAdditionalStarInfoRequestMother.ADDITIONAL_STAR_INFO_REQUEST_1)){
            return STAR_USER_INFORMATION_1.getUserInformationFixture();
        }
        return null;
    }

    public NEOUserInformationDTO getUserInformationFixture(NEOAdditionalStarInfoRequest.NEOAdditionalStarInfoRequestBuilder builder) {
        NEOAdditionalStarInfoRequest createdRequest = builder.build();
        return NEOUserInformationDTO.builder()
                .userType(NEOUserType.STAR)
                .hasWiki(true)
                .isPrivate(true)
                .userID(createdRequest.getUserID())
                .userPW(createdRequest.getUserPW())
                .email(createdRequest.getEmail())
                .userName(createdRequest.getUserName())
                .phoneNumber(createdRequest.getPhoneNumber())
                .neoNickName(createdRequest.getNeoNickName())
                .starNickName(createdRequest.getStarNickName())
                .gender(createdRequest.getGender())
                .starClassificationSet(createdRequest.getStarClassificationSet())
                .submittedUrl(createdRequest.getSubmittedUrl())
                .introduction(createdRequest.getIntroduction())
                .customWikiList(createdRequest.getCustomWikiList())
                .build();
    }
}

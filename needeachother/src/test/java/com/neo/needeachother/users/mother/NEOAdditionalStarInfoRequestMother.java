package com.neo.needeachother.users.mother;

import com.neo.needeachother.users.dto.NEOAdditionalStarInfoRequest;
import com.neo.needeachother.users.dto.NEOStarWikiInformationDTO;
import com.neo.needeachother.users.enums.NEOGenderType;
import com.neo.needeachother.users.enums.NEOStarDetailClassification;
import lombok.Getter;

import java.util.HashSet;
import java.util.List;

public enum NEOAdditionalStarInfoRequestMother {
    ADDITIONAL_STAR_INFO_REQUEST_1("free_minkya", "1234", "free_minkya@naver.com", "가가가",
            "010-1111-1111", "박보영네오", "박보영", NEOGenderType.FEMALE,
            new HashSet<>(List.of(NEOStarDetailClassification.ACTOR, NEOStarDetailClassification.COMEDIAN)),
            List.of("www.youtube.com/@free_minkya"), "안녕하세요, 박보영입니다.",
            List.of(NEOStarWikiInformationDTO.builder().customTitle("MBTI").customContext("ISTJ").build()));

    @Getter
    private final String userID;

    @Getter
    private final String userEmail;

    @Getter
    private final NEOAdditionalStarInfoRequest createRequestFixture;

    private final NEOAdditionalStarInfoRequest.NEOAdditionalStarInfoRequestBuilder createRequestBuilder;

    NEOAdditionalStarInfoRequestMother(String userID, String userPW, String email, String userName, String phoneNumber,
                            String neoNickName, String starNickName, NEOGenderType gender, HashSet<NEOStarDetailClassification> starClassificationSet,
                            List<String> submittedUrl, String introduction, List<NEOStarWikiInformationDTO> customWikiList) {

        this.userID = userID;
        this.userEmail = email;
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
    }

    public NEOAdditionalStarInfoRequest.NEOAdditionalStarInfoRequestBuilder getCreateRequestBuilder(){
        return this.createRequestBuilder.build().toBuilder();
    }
}

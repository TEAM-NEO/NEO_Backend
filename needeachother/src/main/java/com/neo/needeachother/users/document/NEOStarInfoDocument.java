package com.neo.needeachother.users.document;

import com.neo.needeachother.users.dto.NEOCustomStarInformation;
import com.neo.needeachother.users.dto.NEOPublicStarInfoDto;
import com.neo.needeachother.users.dto.NEOStarInfoDto;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 이승훈<br>
 * @since 23.08.21<br>
 * 스타에 저장되는 커스텀 정보입니다.
 */
@Getter
@Builder
@ToString
@Document(collection = "star_custom_info")
public class NEOStarInfoDocument {

    @MongoId
    private ObjectId id;

    @Setter
    private String userID;

    @Setter
    private String introduction;

    @Setter
    private List<NEOCustomStarInformationDocument> starCustomIntroductionList;

    @Setter
    private List<String> submittedUrl;

    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    public static class NEOCustomStarInformationDocument {
        @MongoId
        private ObjectId id;
        private String customTitle;
        private String customContext;

        public NEOCustomStarInformation convertToDtoFormat(){
            return new NEOCustomStarInformation(this.customTitle, this.customContext);
        }

        public static NEOCustomStarInformationDocument fromDTO(NEOCustomStarInformation dto){
            return new NEOCustomStarInformationDocument(null, dto.getCustomTitle(), dto.getCustomContext());
        }
    }

    /**
     * 사용자 요청으로 들어오는 {@code NEOStarInfoDto}로부터 {@code NEOStarInfoDocument}를 생성할 수 있는 정적 팩토리 메서드입니다.<br>
     * @param request 스타 정보 DTO
     * @return {@code NEOStarInfoDocument}
     */
    public static NEOStarInfoDocument fromRequest(NEOStarInfoDto request){
        return NEOStarInfoDocument.builder()
                .userID(request.getUserID())
                .introduction(request.getIntroduction())
                .starCustomIntroductionList(request.getCustomIntroductionList().stream()
                        .map(customInfo -> NEOCustomStarInformationDocument.builder()
                                .customTitle(customInfo.getCustomTitle())
                                .customContext(customInfo.getCustomContext())
                                .build())
                        .collect(Collectors.toList()))
                .submittedUrl(request.getSubmittedUrl())
                .build();
    }

    /**
     * {@code NEOStarInfoDto}로 변환합니다.
     * @return {@code NEOStarInfoDto}
     */
    public NEOStarInfoDto toDTO(){
        return NEOStarInfoDto.builder()
                .userID(this.getUserID())
                .introduction(this.getIntroduction())
                .submittedUrl(this.getSubmittedUrl())
                .customIntroductionList(this.starCustomIntroductionList.stream()
                        .map(NEOCustomStarInformationDocument::convertToDtoFormat)
                        .collect(Collectors.toList()))
                .build();
    }

    /**
     * {@code NEOStarInfoDto}에 {@code NEOStarInfoDocument}에 있는 필드를 덧붙입니다.
     * @param starDto 스타 정보 이동 객체
     * @return {@code NEOStarInfoDto}
     */
    public NEOStarInfoDto fetchDTO(NEOStarInfoDto starDto){
        starDto.setUserID(this.userID);
        starDto.setIntroduction(this.getIntroduction());
        starDto.setSubmittedUrl(this.getSubmittedUrl());
        starDto.setCustomIntroductionList(this.starCustomIntroductionList.stream()
                .map(NEOCustomStarInformationDocument::convertToDtoFormat)
                .collect(Collectors.toList()));
        return starDto;
    }

    /**
     * {@code NEOPublicStarInfoDto}에 {@code NEOStarInfoDocument}에 있는 필드를 덧붙입니다.
     * @param starDto 스타 정보 이동 객체
     * @return {@code NEOPublicStarInfoDto}
     */
    public NEOPublicStarInfoDto fetchPublicDTO(NEOPublicStarInfoDto starDto){
        starDto.setIntroduction(this.getIntroduction());
        starDto.setSubmittedUrl(this.getSubmittedUrl());
        starDto.setCustomIntroductionList(this.starCustomIntroductionList.stream()
                .map(NEOCustomStarInformationDocument::convertToDtoFormat)
                .collect(Collectors.toList()));
        return starDto;
    }

}

package com.neo.needeachother.users.document;

import com.neo.needeachother.users.dto.NEOStarInfoDto;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@ToString
@Document(collection = "star_custom_info")
public class NEOStarInfoDocument {

    @MongoId
    private ObjectId id;

    private String userID;

    private String introduction;

    private List<NEOStarCustomInformation> starCustomIntroductionList;

    private List<String> submittedUrl;

    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    public static class NEOStarCustomInformation {
        @MongoId
        private ObjectId id;
        private String customTitle;
        private String customContext;

        public NEOStarInfoDto.NEOCustomStarInformation convertToDtoFormat(){
            return new NEOStarInfoDto.NEOCustomStarInformation(this.customTitle, this.customContext);
        }
    }

    public static NEOStarInfoDocument fromRequest(NEOStarInfoDto request){
        return NEOStarInfoDocument.builder()
                .userID(request.getUserID())
                .introduction(request.getIntroduction())
                .starCustomIntroductionList(request.getCustomIntroductionList().stream()
                        .map(customInfo -> NEOStarCustomInformation.builder()
                                .customTitle(customInfo.getCustomTitle())
                                .customContext(customInfo.getCustomContext())
                                .build())
                        .collect(Collectors.toList()))
                .submittedUrl(request.getSubmittedUrl())
                .build();
    }

    public NEOStarInfoDto toDTO(){
        return NEOStarInfoDto.builder()
                .userID(this.getUserID())
                .introduction(this.getIntroduction())
                .submittedUrl(this.getSubmittedUrl())
                .customIntroductionList(this.starCustomIntroductionList.stream()
                        .map(NEOStarCustomInformation::convertToDtoFormat)
                        .collect(Collectors.toList()))
                .build();
    }

    public NEOStarInfoDto fetchDTO(NEOStarInfoDto starDto){
        starDto.setUserID(this.userID);
        starDto.setIntroduction(this.getIntroduction());
        starDto.setSubmittedUrl(this.getSubmittedUrl());
        starDto.setCustomIntroductionList(this.starCustomIntroductionList.stream()
                .map(NEOStarCustomInformation::convertToDtoFormat)
                .collect(Collectors.toList()));
        return starDto;
    }

}

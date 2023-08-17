package com.neo.needeachother.users.document;

import com.neo.needeachother.users.request.NEOCreateStarInfoRequest;
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
    }

    public static NEOStarInfoDocument fromRequest(NEOCreateStarInfoRequest request){
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

}

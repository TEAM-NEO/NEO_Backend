package com.neo.needeachother.users.document;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

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
    @AllArgsConstructor
    @ToString
    public static class NEOStarCustomInformation {
        @MongoId
        private ObjectId id;
        private String customTitle;
        private String customContext;
    }

}

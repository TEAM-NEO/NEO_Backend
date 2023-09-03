package com.neo.needeachother.users.document;

import com.neo.needeachother.users.dto.NEOAdditionalStarInfoRequest;
import com.neo.needeachother.users.dto.NEOStarWikiInformationDTO;
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

        public NEOStarWikiInformationDTO convertToDtoFormat(){
            return new NEOStarWikiInformationDTO(this.customTitle, this.customContext);
        }

        public static NEOCustomStarInformationDocument fromDTO(NEOStarWikiInformationDTO dto){
            return new NEOCustomStarInformationDocument(null, dto.getCustomTitle(), dto.getCustomContext());
        }
    }

    /**
     * 사용자 요청으로 들어오는 {@code NEOAdditionalStarInfoRequest}로부터 {@code NEOStarInfoDocument}를 생성할 수 있는 정적 팩토리 메서드입니다.<br>
     * @param request 스타 정보 DTO
     * @return {@code NEOStarInfoDocument}
     */
    public static NEOStarInfoDocument fromRequest(NEOAdditionalStarInfoRequest request){
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


}

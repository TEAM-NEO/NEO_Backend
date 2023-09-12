package com.neo.needeachother.users.dto;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.neo.needeachother.users.document.NEOStarInfoDocument;
import com.neo.needeachother.users.entity.NEOStarEntity;
import com.neo.needeachother.users.entity.NEOUserEntity;
import com.neo.needeachother.users.enums.NEOGenderType;
import com.neo.needeachother.users.enums.NEOStarDetailClassification;
import com.neo.needeachother.users.enums.NEOUserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;

@Getter
@Builder(toBuilder = true)
@JsonFilter("NEOInfoDtoJsonFilter")
@NoArgsConstructor
@AllArgsConstructor
public class NEOUserInformationDTO {

    @JsonProperty(value = "user_type")
    private NEOUserType userType;

    @JsonProperty(value = "has_wiki")
    private boolean hasWiki;

    @JsonProperty(value = "is_private")
    private boolean isPrivate;

    @JsonProperty(value = "user_id")
    private String userID;

    @JsonProperty(value = "user_pw")
    private String userPW;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "user_name")
    private String userName;

    @JsonProperty(value = "phone_number")
    private String phoneNumber;

    @JsonProperty(value = "nickname")
    private String neoNickName;

    @JsonProperty(value = "star_nickname")
    private String starNickName;

    @JsonProperty(value = "gender")
    private NEOGenderType gender;

    @JsonProperty(value = "star_classification_list")
    private HashSet<NEOStarDetailClassification> starClassificationSet;

    @JsonProperty("submitted_url")
    private List<String> submittedUrl;

    @JsonProperty(value = "introduction")
    private String introduction;

    @JsonProperty("custom_wiki_list")
    private List<NEOStarWikiInformationDTO> customWikiList;

    public static NEOUserInformationDTO from(NEOUserEntity fanEntity, boolean isPrivate) {
        if (isPrivate) {
            return NEOUserInformationDTO.builder()
                    .userType(fanEntity.getUserType())
                    .hasWiki(false)
                    .isPrivate(isPrivate)
                    .userID(fanEntity.getNeoID())
                    .userName(fanEntity.getUserName())
                    .email(fanEntity.getEmail())
                    .phoneNumber(fanEntity.getPhoneNumber())
                    .neoNickName(fanEntity.getNeoNickName())
                    .gender(fanEntity.getGender())
                    .build();
        }

        return NEOUserInformationDTO.builder()
                .userType(fanEntity.getUserType())
                .hasWiki(false)
                .isPrivate(isPrivate)
                .userID(fanEntity.getNeoID())
                .neoNickName(fanEntity.getNeoNickName())
                .gender(fanEntity.getGender())
                .build();
    }

    public static NEOUserInformationDTO from(final NEOStarEntity starEntity, final NEOStarInfoDocument starInfoDocument,
                                             final boolean isPrivate, final boolean hasWiki) {

        List<NEOStarWikiInformationDTO> customWikiList = null;

        if (hasWiki) {
            customWikiList = starInfoDocument.getStarCustomIntroductionList().stream()
                    .map(NEOStarInfoDocument.NEOCustomStarInformationDocument::convertToDtoFormat)
                    .toList();
        }

        if (isPrivate) {
            return NEOUserInformationDTO.builder()
                    .userType(starEntity.getUserInformation().getUserType())
                    .hasWiki(hasWiki)
                    .isPrivate(isPrivate)
                    .userID(starEntity.getUserInformation().getNeoID())
                    .userName(starEntity.getUserInformation().getUserName())
                    .email(starEntity.getUserInformation().getEmail())
                    .phoneNumber(starEntity.getUserInformation().getPhoneNumber())
                    .neoNickName(starEntity.getUserInformation().getNeoNickName())
                    .starNickName(starEntity.getStarNickName())
                    .gender(starEntity.getUserInformation().getGender())
                    .starClassificationSet(starEntity.getStarClassificationSet())
                    .submittedUrl(starInfoDocument.getSubmittedUrl())
                    .introduction(starInfoDocument.getIntroduction())
                    .customWikiList(customWikiList)
                    .build();
        }

        return NEOUserInformationDTO.builder()
                .userType(starEntity.getUserInformation().getUserType())
                .hasWiki(hasWiki)
                .isPrivate(isPrivate)
                .userID(starEntity.getUserInformation().getNeoID())
                .neoNickName(starEntity.getUserInformation().getNeoNickName())
                .starNickName(starEntity.getStarNickName())
                .gender(starEntity.getUserInformation().getGender())
                .starClassificationSet(starEntity.getStarClassificationSet())
                .submittedUrl(starInfoDocument.getSubmittedUrl())
                .introduction(starInfoDocument.getIntroduction())
                .customWikiList(customWikiList)
                .build();
    }
}

package com.neo.needeachother.users.filter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.neo.needeachother.users.dto.NEOUserInformationDTO;
import com.neo.needeachother.users.enums.NEOUserType;

import java.util.HashSet;
import java.util.List;


/**
 * @author 이승훈<br>
 * @since 23.08.21<br>
 * {@code NEOAdditionalFanInfoRequest}, {@code NEOAdditionalStarInfoRequest}등 정보 응답 객체 적용하는 JsonFilter입니다.<br>
 * 아래의 로직을 통해 자바 객체가 Json으로 직렬화 되는 로직을 제어합니다.<br>
 */
public class NEOInfoDtoJsonFilter extends SimpleBeanPropertyFilter {

    // JSON 변환 시점에 대상 POJO의 모든 필드에 대해 실행된다.
    @Override
    public void serializeAsField(Object pojo, JsonGenerator jgen, SerializerProvider provider, PropertyWriter writer) throws Exception {
        if (pojo instanceof NEOUserInformationDTO) {
            if (canSerializeAsFieldInUserInfoDto((NEOUserInformationDTO) pojo, writer.getName())) {
                super.serializeAsField(pojo, jgen, provider, writer);
            }
        } else {
            super.serializeAsField(pojo, jgen, provider, writer);
        }
    }

    /**
     * {@code NEOUserInformationDTO}의 각 필드를 직렬화 할 지에 대한 여부를 결정하는 메소드입니다.
     * @param response NEO 유저 정보 응답
     * @param fieldName 스타 정보 응답의 각각의 필드 명 (@JsonProperty의 name을 따라감.)
     * @return {@code boolean} 직렬화 여부
     */
    private boolean canSerializeAsFieldInUserInfoDto(NEOUserInformationDTO response, String fieldName) {
        if (response.getUserType() == NEOUserType.FAN){
            return canSerializeAsFieldInFanInfoDto(response, fieldName);
        } else {
            return canSerializeAsFieldInStarInfoDto(response, fieldName);
        }
    }

    private boolean canSerializeAsFieldInFanInfoDto(NEOUserInformationDTO response, String fieldName) {
        HashSet<String> onlyStarHasFieldSet = new HashSet<>(List.of("star_nickname", "star_classification_list", "submitted_url", "introduction", "custom_wiki_list"));
        HashSet<String> privateFieldSet = new HashSet<>(List.of("email", "user_name", "phone_number"));

        // 비밀번호는 노출 X, 스타만 가지고 있는 필드라면 노출 X
        if (fieldName.equals("user_pw") || onlyStarHasFieldSet.contains(fieldName)){
            return false;
        }

        // 공개 정보만 필요한 경우, 개인 정보 노출 X
        return response.isPrivate() || !privateFieldSet.contains(fieldName);
    }

    private boolean canSerializeAsFieldInStarInfoDto(NEOUserInformationDTO response, String fieldName) {
        HashSet<String> privateFieldSet = new HashSet<>(List.of("email", "user_name", "phone_number"));

        // 비밀번호는 노출 X, 공개 정보만 원할 시 개인 정보 노출 X
        if (fieldName.equals("user_pw") || (!response.isPrivate() && privateFieldSet.contains(fieldName))){
            return false;
        }

        // wiki 불포함 선택 시 custom_wiki_list 제거
        return response.isHasWiki() || !fieldName.equals("custom_wiki_list");
    }


}
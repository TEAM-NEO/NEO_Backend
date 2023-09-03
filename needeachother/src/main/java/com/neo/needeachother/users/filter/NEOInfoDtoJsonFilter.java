package com.neo.needeachother.users.filter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.neo.needeachother.users.dto.NEOAdditionalFanInfoRequest;
import com.neo.needeachother.users.dto.NEOAdditionalStarInfoRequest;


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
        if (pojo instanceof NEOAdditionalStarInfoRequest) {
            if (canSerializeAsFieldInStarInfoDto((NEOAdditionalStarInfoRequest) pojo, writer.getName())) {
                super.serializeAsField(pojo, jgen, provider, writer);
            }
        } else if (pojo instanceof NEOAdditionalFanInfoRequest){
            if (canSerializeAsFieldInFanInfoDto((NEOAdditionalFanInfoRequest) pojo, writer.getName())) {
                super.serializeAsField(pojo, jgen, provider, writer);
            }
        }
        else {
            super.serializeAsField(pojo, jgen, provider, writer);
        }
    }

    /**
     * {@code NEOAdditionalStarInfoRequest}의 필드를 직렬화 할 지에 대한 여부를 결정하는 메소드입니다.
     * @param response 스타 정보 응답
     * @param fieldName 스타 정보 응답의 각각의 필드 명 (@JsonProperty의 name을 따라감.)
     * @return {@code boolean} 직렬화 여부
     */
    private boolean canSerializeAsFieldInStarInfoDto(NEOAdditionalStarInfoRequest response, String fieldName) {
        if(fieldName.equals("user_pw")){
            return false;
        }
        return !fieldName.equals("custom_introduction_list") || (response.getCustomIntroductionList() != null && !response.getCustomIntroductionList().isEmpty());
    }

    /**
     * {@code NEOAdditionalFanInfoRequest}의 필드를 직렬화 할 지에 대한 여부를 결정하는 메소드입니다.
     * @param response 스타 정보 응답
     * @param fieldName 스타 정보 응답의 각각의 필드 명 (@JsonProperty의 name을 따라감.)
     * @return {@code boolean} 직렬화 여부
     */
    private boolean canSerializeAsFieldInFanInfoDto(NEOAdditionalFanInfoRequest response, String fieldName) {
        return !fieldName.equals("user_pw") && !fieldName.equals("favorite_star_id");
    }


}
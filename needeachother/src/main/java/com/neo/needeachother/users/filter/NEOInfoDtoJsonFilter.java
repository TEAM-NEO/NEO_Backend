package com.neo.needeachother.users.filter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.neo.needeachother.users.dto.NEOFanInfoDto;
import com.neo.needeachother.users.dto.NEOStarInfoDto;

public class NEOInfoDtoJsonFilter extends SimpleBeanPropertyFilter {

    // JSON 변환 시점에 대상 POJO의 모든 필드에 대해 실행된다.
    @Override
    public void serializeAsField(Object pojo, JsonGenerator jgen, SerializerProvider provider, PropertyWriter writer) throws Exception {
        if (pojo instanceof NEOStarInfoDto) {
            if (canSerializeAsFieldInStarInfoDto((NEOStarInfoDto) pojo, writer.getName())) {
                super.serializeAsField(pojo, jgen, provider, writer);
            }
        } else if (pojo instanceof NEOFanInfoDto){
            if (canSerializeAsFieldInFanInfoDto((NEOFanInfoDto) pojo, writer.getName())) {
                super.serializeAsField(pojo, jgen, provider, writer);
            }
        }
        else {
            super.serializeAsField(pojo, jgen, provider, writer);
        }
    }

    private boolean canSerializeAsFieldInStarInfoDto(NEOStarInfoDto response, String fieldName) {
        if(fieldName.equals("user_pw")){
            return false;
        }
        return !fieldName.equals("custom_introduction_list") || (response.getCustomIntroductionList() != null && !response.getCustomIntroductionList().isEmpty());
    }

    private boolean canSerializeAsFieldInFanInfoDto(NEOFanInfoDto response, String fieldName) {
        return !fieldName.equals("user_pw") && !fieldName.equals("favorite_star_id");
    }


}
package com.neo.needeachother.common.filter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.neo.needeachother.common.enums.NEOResponseCode;
import com.neo.needeachother.common.response.NEOResponseBody;

public class NEOResponseJsonFilter extends SimpleBeanPropertyFilter {

    // JSON 변환 시점에 대상 POJO의 모든 필드에 대해 실행된다.
    @Override
    public void serializeAsField(Object pojo, JsonGenerator jgen, SerializerProvider provider, PropertyWriter writer) throws Exception {
        if (pojo instanceof NEOResponseBody<?>) {
            if (canSerializeAsField((NEOResponseBody<?>) pojo, writer.getName())) {
                super.serializeAsField(pojo, jgen, provider, writer);
            }
        } else {
            super.serializeAsField(pojo, jgen, provider, writer);
        }
    }

    private boolean canSerializeAsField(NEOResponseBody<?> response, String fieldName) {
        if(fieldName.equals("data") && response.getResponseCode().equals(NEOResponseCode.FAIL)){
            return false;
        }
        if(fieldName.equals("data") && response.getData() == null){
            return false;
        }
        return !fieldName.equals("errors") || response.getResponseCode().equals(NEOResponseCode.FAIL);
    }

}

package com.neo.needeachother.common.filter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.neo.needeachother.common.enums.NEOResponseCode;
import com.neo.needeachother.common.response.NEOFinalErrorResponse;

/**
 * @author 이승훈<br>
 * @since 23.08.21<br>
 * {@code NEOFinalErrorResponse}에 적용하는 Json Filter입니다.<br>
 * 아래의 로직을 통해 자바 객체가 Json으로 직렬화 되는 로직을 제어합니다.<br>
 */
public class NEOResponseJsonFilter extends SimpleBeanPropertyFilter {

    // JSON 변환 시점에 대상 POJO의 모든 필드에 대해 실행된다.
    @Override
    public void serializeAsField(Object pojo, JsonGenerator jgen, SerializerProvider provider, PropertyWriter writer) throws Exception {
        if (pojo instanceof NEOFinalErrorResponse) {
            if (canSerializeAsField((NEOFinalErrorResponse) pojo, writer.getName())) {
                super.serializeAsField(pojo, jgen, provider, writer);
            }
        } else {
            super.serializeAsField(pojo, jgen, provider, writer);
        }
    }

    /**
     * 실패 시 혹은 {@code data}가 {@code null}인 경우 필드 직렬화 제외.<br>
     * {@code NEOResponseCode}가 {@code NEOResponseCode.FAIL}인 경우에는 {@code errors} 필드 직렬화.<br>
     * 나머지 필드는 모두 직렬화 진행.<br>
     * @param response 최종 응답 객체
     * @param fieldName 최종 응답 객체의 각 필드 이름
     * @return {@code boolean} 직렬화 여부
     */
    private boolean canSerializeAsField(NEOFinalErrorResponse response, String fieldName) {
        return !fieldName.equals("errors") || response.getResponseCode().equals(NEOResponseCode.FAIL);
    }

}

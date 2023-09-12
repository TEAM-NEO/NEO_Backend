package com.neo.needeachother.users.converter;

import com.neo.needeachother.users.enums.NEOUserType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * {@code NEOUserType}을 Entity에 저장할 때와 Enum으로 변환하는 작업을 수행하는 컨버터입니다.
 */
@Converter
public class NEOUserTypeConverter implements AttributeConverter<NEOUserType, String> {

    @Override
    public String convertToDatabaseColumn(NEOUserType attribute) {
        return attribute.getTypeCode();
    }

    @Override
    public NEOUserType convertToEntityAttribute(String dbData) {
        return NEOUserType.convertTypeCodeToEnum(dbData);
    }

}

package com.neo.needeachother.users.converter;

import com.neo.needeachother.users.enums.NEOGenderType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * 23.08.12 이승훈 <br>
 * {@code NEOGenderType}을 엔티티에서 사용할 때, DB에 어떻게 저장할 것인지, 저장된 값을 어떻게 다시 Enum으로 살릴지 결정하는 컨버터입니다.
 */
@Converter
public class NEOGenderTypeConverter implements AttributeConverter<NEOGenderType, String> {

    /**
     * {@code NEOGenderType}을 데이터베이스 컬럼에 저장할 때 값을 어떻게 변환시킬지를 결정하는 메소드입니다.<br>
     * 데이터 축약을 위해 {@code NEOGenderType}의 {@code GenderCode}를 사용합니다.
     * @param attribute {@code NEOGenderType} Enum 타입
     * @return DB column String data
     */
    @Override
    public String convertToDatabaseColumn(NEOGenderType attribute) {
        return attribute.getGenderCode();
    }

    /**
     * 저장된 데이터베이스 컬럼을 애플리케이션 코드의 엔티티에서 필드로 사용할 객제로 변환시키는 메소드입니다.<br>
     * {@code NEOGenderType}의 {@code genderCode}로 저장된 것을, 다시 {@code NEOGenderType}으로 변환합니다.
     * @param dbData 데이터베이스에 저장된 {@code genderCode}
     * @return {@code NEOGenderType}
     */
    @Override
    public NEOGenderType convertToEntityAttribute(String dbData) {
        return NEOGenderType.convertGenderCodeToEnum(dbData);
    }

}

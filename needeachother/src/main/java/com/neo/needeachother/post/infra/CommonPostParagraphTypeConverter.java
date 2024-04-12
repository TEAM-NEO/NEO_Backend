package com.neo.needeachother.post.infra;

import com.neo.needeachother.post.domain.CommonPostParagraphType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CommonPostParagraphTypeConverter implements AttributeConverter<CommonPostParagraphType, String> {

    @Override
    public String convertToDatabaseColumn(CommonPostParagraphType attribute) {
        return attribute.getCommonPostParagraphTypeSummarizedSymbol();
    }

    @Override
    public CommonPostParagraphType convertToEntityAttribute(String dbData) {
        return CommonPostParagraphType.convertToCommonPostContentType(dbData);
    }

}

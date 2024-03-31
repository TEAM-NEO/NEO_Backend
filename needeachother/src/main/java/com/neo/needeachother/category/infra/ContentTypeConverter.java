package com.neo.needeachother.category.infra;

import com.neo.needeachother.category.domain.ContentType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ContentTypeConverter implements AttributeConverter<ContentType, String> {

    @Override
    public String convertToDatabaseColumn(ContentType attribute) {
        return attribute.getContentTypeSummarizedSymbol();
    }

    @Override
    public ContentType convertToEntityAttribute(String dbData) {
        return ContentType.convertToContentType(dbData);
    }
}

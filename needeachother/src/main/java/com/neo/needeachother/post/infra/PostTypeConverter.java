package com.neo.needeachother.post.infra;

import com.neo.needeachother.post.domain.PostType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;


@Converter
public class PostTypeConverter implements AttributeConverter<PostType, String> {

    @Override
    public String convertToDatabaseColumn(PostType attribute) {
        return attribute.getContentTypeSummarizedSymbol();
    }

    @Override
    public PostType convertToEntityAttribute(String dbData) {
        return PostType.convertToPostType(dbData);
    }
}

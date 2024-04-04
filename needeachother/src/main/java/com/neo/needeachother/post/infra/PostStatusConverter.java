package com.neo.needeachother.post.infra;

import com.neo.needeachother.post.domain.PostStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PostStatusConverter implements AttributeConverter<PostStatus, String> {

    @Override
    public String convertToDatabaseColumn(PostStatus attribute) {
        return attribute.getPostStatusSummarizedSymbol();
    }

    @Override
    public PostStatus convertToEntityAttribute(String dbData) {
        return PostStatus.convertToPostStatus(dbData);
    }
}

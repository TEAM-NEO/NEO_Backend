package com.neo.needeachother.category.infra;

import com.neo.needeachother.category.domain.CategoryStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CategoryStatusConverter implements AttributeConverter<CategoryStatus, String> {

    @Override
    public String convertToDatabaseColumn(CategoryStatus attribute) {
        return attribute.getCategorySummarizedSymbol();
    }

    @Override
    public CategoryStatus convertToEntityAttribute(String dbData) {
        return CategoryStatus.convertToCategoryStatus(dbData);
    }

}


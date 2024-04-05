package com.neo.needeachother.category.domain.domainservice;

import com.neo.needeachother.category.domain.CategoryId;
import com.neo.needeachother.category.domain.ContentType;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryIdGenerateService {
    public CategoryId generateNextId(ContentType contentType) {
        String stringBuilder = contentType.getPrefixCategoryId() +
                "_" +
                UUID.randomUUID().toString().toUpperCase();
        return new CategoryId(stringBuilder);
    }
}

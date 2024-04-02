package com.neo.needeachother.category.infra;

import com.neo.needeachother.category.domain.CategoryId;
import com.neo.needeachother.category.domain.ContentType;
import com.neo.needeachother.category.domain.repository.CategoryCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class CategoryCustomRepositoryImpl implements CategoryCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public CategoryId getNextId(ContentType contentType) {
        String stringBuilder = contentType.getPrefixCategoryId() +
                "_" +
                UUID.randomUUID().toString().toUpperCase();
        return new CategoryId(stringBuilder);
    }
}

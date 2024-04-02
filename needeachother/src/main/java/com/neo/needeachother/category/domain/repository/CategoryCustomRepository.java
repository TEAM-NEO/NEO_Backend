package com.neo.needeachother.category.domain.repository;

import com.neo.needeachother.category.domain.CategoryId;
import com.neo.needeachother.category.domain.ContentType;

public interface CategoryCustomRepository {
    CategoryId getNextId(ContentType contentType);
}

package com.neo.needeachother.category.domain.repository;

import com.neo.needeachother.category.domain.Category;
import com.neo.needeachother.category.domain.CategoryId;
import com.neo.needeachother.category.domain.ContentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CategoryCustomRepository, JpaRepository<Category, CategoryId> {
}


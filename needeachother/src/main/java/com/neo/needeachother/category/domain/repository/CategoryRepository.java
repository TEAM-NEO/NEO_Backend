package com.neo.needeachother.category.domain.repository;

import com.neo.needeachother.category.domain.Category;
import com.neo.needeachother.category.domain.CategoryId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, CategoryId>, CategoryCustomRepository {
}

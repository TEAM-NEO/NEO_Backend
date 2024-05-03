package com.neo.needeachother.category.infra;

import com.neo.needeachother.category.domain.CategoryStatus;
import com.neo.needeachother.category.domain.dto.CategoryDetailViewData;
import com.neo.needeachother.category.domain.dto.CategoryDetailViewDto;
import com.neo.needeachother.category.domain.dto.CategoryViewData;
import com.neo.needeachother.category.domain.dto.CategoryViewDto;
import com.neo.needeachother.category.domain.repository.CategoryRepositoryCustom;
import com.neo.needeachother.common.exception.NEOUnexpectedException;
import com.neo.needeachother.starpage.domain.StarPageId;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.neo.needeachother.category.domain.QCategory.category;
import static com.neo.needeachother.post.domain.QStarPagePost.starPagePost;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<CategoryViewDto> searchCategoryViewByStarPageId(StarPageId starPageId) {
        List<CategoryViewDto> queryResult = jpaQueryFactory
                .select(
                        Expressions.asString(starPageId.getValue()).as("starPageId"),
                        category.categoryId.value,
                        category.categoryInformation.categoryTitle,
                        category.contentType.stringValue(),
                        starPagePost.count()
                )
                .from(category)
                .innerJoin(starPagePost).on(starPagePost.categoryId.eq(category.categoryId))
                .groupBy(category.categoryId)
                .where(
                        categoryEqStarPageId(starPageId),
                        neCategoryStatusDeleted()
                )
                .transform(GroupBy.groupBy(category.starPageId)
                        .list(Projections.constructor(CategoryViewDto.class,
                                Expressions.asString(starPageId.getValue()).as("starPageId"),
                                GroupBy.list(
                                        Projections.constructor(CategoryViewData.class,
                                                category.categoryId.value,
                                                category.categoryInformation.categoryTitle,
                                                category.contentType.stringValue(),
                                                starPagePost.count())
                                )
                        )));

        if (queryResult.size() > 1) {
            throw new NEOUnexpectedException("정상적이지 않은 결과, searchCategoryViewByStarPageId");
        }

        return Optional.ofNullable(queryResult.isEmpty() ? null : queryResult.get(0));
    }

    @Override
    public Optional<CategoryDetailViewDto> searchCategoryDetailViewByStarPageId(StarPageId starPageId) {
        List<CategoryDetailViewDto> queryResult = jpaQueryFactory
                .select(category, starPagePost.count())
                .from(category)
                .innerJoin(starPagePost).on(starPagePost.categoryId.eq(category.categoryId))
                .groupBy(category.categoryId)
                .where(
                        categoryEqStarPageId(starPageId),
                        neCategoryStatusDeleted()
                ).transform(GroupBy.groupBy(category.starPageId)
                        .list(
                                Projections.constructor(CategoryDetailViewDto.class,
                                        Expressions.asString(starPageId.getValue()).as("starPageId"),
                                        GroupBy.list(Projections.constructor(CategoryDetailViewData.class,
                                                category.categoryId.value,
                                                category.categoryInformation.categoryTitle,
                                                category.contentType.stringValue(),
                                                category.restriction.onlyHostWriteContent,
                                                category.restriction.isWriteAbleComment,
                                                category.restriction.useCommentRatingFilter,
                                                category.restriction.filteringRate,
                                                starPagePost.count()
                                        ))
                                )
                        ));

        if (queryResult.size() > 1) {
            throw new NEOUnexpectedException("정상적이지 않은 결과, searchCategoryDetailViewByStarPageId");
        }

        return Optional.ofNullable(queryResult.isEmpty() ? null : queryResult.get(0));
    }

    private BooleanExpression categoryEqStarPageId(StarPageId starPageId) {
        return category.starPageId.eq(starPageId);
    }

    private BooleanExpression neCategoryStatusDeleted() {
        return category.categoryStatus.ne(CategoryStatus.DELETED);
    }
}

package com.neo.needeachother.post.infra;

import com.neo.needeachother.category.domain.CategoryId;
import com.neo.needeachother.post.domain.*;
import com.neo.needeachother.post.domain.dto.PostSearchCondition;
import com.neo.needeachother.post.domain.dto.StarPagePostHeadlineDto;
import com.neo.needeachother.post.domain.repository.PostRepositoryCustom;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.neo.needeachother.post.domain.QAlbumPost.albumPost;
import static com.neo.needeachother.post.domain.QCommonPost.commonPost;
import static com.neo.needeachother.post.domain.QGoldBalancePost.goldBalancePost;
import static com.neo.needeachother.post.domain.QStarPagePost.starPagePost;
import static com.neo.needeachother.category.domain.QCategory.category;
import static com.neo.needeachother.post.domain.QVoteItem.voteItem;
import static com.neo.needeachother.post.domain.QVotePost.votePost;
import static com.neo.needeachother.starpage.domain.QStarPage.starPage;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<StarPagePostHeadlineDto> searchPostHeadlineByCategoryIdWithPaging(CategoryId categoryId, PostSearchCondition postSearchCondition, Pageable pageable) {
        List<StarPagePostHeadlineDto> content = jpaQueryFactory.select(
                        Projections.constructor(StarPagePostHeadlineDto.class,
                                Expressions.asString(categoryId.getValue()).as("categoryId"),
                                category.categoryInformation.categoryTitle,
                                starPagePost.id,
                                starPagePost.likeCount,
                                starPagePost.author.authorName,
                                starPagePost.title,
                                starPagePost.createdAt,
                                starPagePost.hostHeart,
                                starPagePost.postType.stringValue(),
                                starPagePost.status.stringValue(),
                                Expressions.asBoolean(starPagePost.createdAt.ne(starPagePost.updatedAt)).as("isChanged")
                        )

                )
                .from(starPagePost)
                .innerJoin(category).on(starPagePost.categoryId.eq(category.categoryId))
                .innerJoin(starPage).on(category.starPageId.eq(starPage.starPageId))
                .where(
                        postEqCategoryId(categoryId),
                        postEqHostHearted(postSearchCondition),
                        postAuthorEqHost(postSearchCondition)
                )
                .orderBy(orderBySearchCondition(postSearchCondition))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> countQuery =
                jpaQueryFactory.select(starPagePost.count())
                        .from(starPagePost)
                        .where(postEqCategoryId(categoryId));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public Slice<StarPagePostHeadlineDto> searchPostHeadlineByCategoryIdWithNoOffset(CategoryId categoryId, PostSearchCondition postSearchCondition, Long lastPostId, Pageable pageable) {
        List<StarPagePostHeadlineDto> content = jpaQueryFactory.select(
                        Projections.constructor(StarPagePostHeadlineDto.class,
                                Expressions.asString(categoryId.getValue()).as("categoryId"),
                                category.categoryInformation.categoryTitle,
                                starPagePost.id,
                                starPagePost.likeCount,
                                starPagePost.author.authorName,
                                starPagePost.title,
                                starPagePost.createdAt,
                                starPagePost.hostHeart,
                                starPagePost.postType.stringValue(),
                                starPagePost.status.stringValue(),
                                Expressions.asBoolean(starPagePost.createdAt.ne(starPagePost.updatedAt)).as("isChanged")
                        )

                )
                .from(starPagePost)
                .innerJoin(category).on(starPagePost.categoryId.eq(category.categoryId))
                .innerJoin(starPage).on(category.starPageId.eq(starPage.starPageId))
                .where(
                        postLessThanLastPostId(lastPostId),
                        postEqCategoryId(categoryId),
                        postEqHostHearted(postSearchCondition),
                        postAuthorEqHost(postSearchCondition)
                )
                .orderBy(orderBySearchCondition(postSearchCondition))
                .limit(pageable.getPageSize() + 1)
                .offset(pageable.getOffset())
                .fetch();

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            hasNext = true;
            content.remove(pageable.getPageSize());
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }

    @Override
    public Optional<CommonPost> findCommonPostById(Long postId){
        return Optional.ofNullable(
                jpaQueryFactory.select(commonPost)
                .from(commonPost)
                .innerJoin(commonPost.commonPostContents).fetchJoin()
                .where(commonPost.id.eq(postId))
                .fetchOne()
        );
    }

    @Override
    public Optional<AlbumPost> findAlbumPostById(Long postId){
        return Optional.ofNullable(
                jpaQueryFactory.select(albumPost)
                        .from(albumPost)
                        .where(albumPost.id.eq(postId))
                        .fetchOne()
        );
    }

    @Override
    public Optional<GoldBalancePost> findGoldBalancePostById(Long postId){
        return Optional.ofNullable(
                jpaQueryFactory.select(goldBalancePost)
                        .from(goldBalancePost)
                        .where(goldBalancePost.id.eq(postId))
                        .fetchOne()
        );
    }

    @Override
    public Optional<VotePost> findVotePostById(Long postId){
        return Optional.ofNullable(
                jpaQueryFactory.select(votePost)
                        .from(votePost)
                        .innerJoin(votePost.voteItems, voteItem).fetchJoin()
                        .where(votePost.id.eq(postId))
                        .fetchOne()
        );
    }

    private OrderSpecifier[] orderBySearchCondition(PostSearchCondition postSearchCondition) {

        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();
        if (Objects.isNull(postSearchCondition)) {
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, starPagePost.createdAt));
            return orderSpecifiers.toArray(new OrderSpecifier[0]);
        }

        if (postSearchCondition.isOrderByCreatedAt()) {
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, starPagePost.createdAt));
        }

        if (postSearchCondition.isOrderByLikeCount()) {
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, starPagePost.likeCount));
        }

        return orderSpecifiers.toArray(new OrderSpecifier[0]);
    }

    private BooleanExpression postEqCategoryId(CategoryId categoryId) {
        return starPagePost.categoryId.eq(categoryId);
    }

    private BooleanExpression postLessThanLastPostId(Long postId) {
        return starPagePost.id.lt(postId);
    }

    private BooleanExpression postEqHostHearted(PostSearchCondition postSearchCondition) {
        if (Objects.isNull(postSearchCondition)) {
            return null;
        }
        return postSearchCondition.isOnlySearchHostHearted() ? starPagePost.hostHeart.eq(true) : null;
    }

    private BooleanExpression postAuthorEqHost(PostSearchCondition postSearchCondition) {
        if (Objects.isNull(postSearchCondition)) {
            return null;
        }
        return postSearchCondition.isOnlySearchHostWritten() ?
                starPagePost.author.authorName.eq(starPage.information.host.starNickName) : null;
    }
}

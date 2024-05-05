package com.neo.needeachother.starpage.infra;

import com.neo.needeachother.category.domain.CategoryId;
import com.neo.needeachother.post.domain.PostStatus;
import com.neo.needeachother.starpage.domain.StarPage;
import com.neo.needeachother.starpage.domain.StarPageId;
import com.neo.needeachother.starpage.domain.dto.*;
import com.neo.needeachother.starpage.domain.repository.StarPageRepositoryCustom;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import static com.neo.needeachother.starpage.domain.QStarPageInfo.starPageInfo;
import static com.neo.needeachother.starpage.domain.QStarPageLayoutLine.starPageLayoutLine;
import static com.querydsl.core.group.GroupBy.list;
import static com.querydsl.core.group.GroupBy.map;

import java.util.List;
import java.util.Optional;

import static com.neo.needeachother.category.domain.QCategory.category;
import static com.neo.needeachother.post.domain.QAlbumPost.albumPost;
import static com.neo.needeachother.post.domain.QCommonPost.commonPost;
import static com.neo.needeachother.post.domain.QGoldBalancePost.goldBalancePost;
import static com.neo.needeachother.post.domain.QStarPagePost.starPagePost;
import static com.neo.needeachother.post.domain.QVoteItem.voteItem;
import static com.neo.needeachother.post.domain.QVotePost.votePost;
import static com.neo.needeachother.starpage.domain.QStarPage.starPage;

@Repository
@RequiredArgsConstructor
public class StarPageRepositoryImpl implements StarPageRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<RepresentativeArticleHeadLine> searchHeadLineByStarPageIdAndLimit(StarPageId id, long limit) {
        // 카테고리명, 제목, 작성자, 좋아요 수, 글 내용 확인 API Path, 대표 이미지 DTO에 담기.
        return queryFactory
                .select(
                        Projections.constructor(
                                RepresentativeArticleHeadLine.class,
                                starPagePost.id, starPagePost.likeCount, starPagePost.author.authorName,
                                category.categoryInformation.categoryTitle, starPagePost.postType.stringValue(), starPagePost.title,
                                albumPost.image.path.coalesce(commonPost.representativeImage).as("representativeImage"),
                                Expressions.asString("베스트 팬 게시물").as("tapName")
                        ))
                .from(starPagePost)
                .innerJoin(category).on(starPagePost.categoryId.eq(category.categoryId))
                .leftJoin(albumPost).on(eqPostId(albumPost.id))
                .leftJoin(commonPost).on(eqPostId(commonPost.id))
                .leftJoin(starPage).on(starPage.starPageId.eq(category.starPageId))
                .where(
                        eqStarPageId(id),
                        neHostEmail(starPage.information.host.email),
                        eqPostStatus(PostStatus.MAIN_EXPOSED)
                )
                .orderBy(starPagePost.exposureAt.desc())
                .limit(limit)
                .fetch();
    }

    @Override
    public List<RepresentativeArticleHeadLine> searchRecentHostHeadLineByStarPageIdAndLimit(StarPageId id, long limit) {
        return queryFactory
                .select(
                        Projections.constructor(
                                RepresentativeArticleHeadLine.class,
                                starPagePost.id, starPagePost.likeCount, starPagePost.author.authorName,
                                category.categoryInformation.categoryTitle, starPagePost.postType.stringValue(), starPagePost.title,
                                albumPost.image.path.coalesce(commonPost.representativeImage).as("representativeImage"),
                                Expressions.asString("최신 스타 게시물").as("tapName")
                        )
                )
                .from(starPagePost)
                .innerJoin(category).on(starPagePost.categoryId.eq(category.categoryId))
                .leftJoin(albumPost).on(eqPostId(albumPost.id))
                .leftJoin(commonPost).on(eqPostId(commonPost.id))
                .leftJoin(starPage).on(starPage.starPageId.eq(category.starPageId))
                .where(
                        eqStarPageId(id),
                        eqHostEmail(starPage.information.host.email),
                        eqPostStatus(PostStatus.MAIN_EXPOSED)
                )
                .orderBy(starPagePost.exposureAt.desc())
                .limit(limit)
                .fetch();
    }

    @Override
    public List<CommonPostHeadLine> searchCommonPostHeadLineByCategoryId(StarPageId starPageId, CategoryId categoryId, long limit) {
        return queryFactory
                .select(
                        Projections.constructor(
                                CommonPostHeadLine.class,
                                starPagePost.id, starPagePost.likeCount, starPagePost.author.authorName,
                                starPagePost.title,
                                commonPost.representativeImage
                        )
                )
                .from(starPagePost)
                .innerJoin(category).on(starPagePost.categoryId.eq(category.categoryId))
                .leftJoin(commonPost).on(eqPostId(commonPost.id))
                .leftJoin(starPage).on(starPage.starPageId.eq(category.starPageId))
                .where(
                        eqStarPageId(starPageId),
                        eqCategoryId(categoryId),
                        eqPostStatus(PostStatus.OPEN).or(eqPostStatus(PostStatus.MAIN_EXPOSED))
                )
                .orderBy(starPagePost.createdAt.desc())
                .limit(limit)
                .fetch();
    }

    @Override
    public List<ImagePostHeadLine> searchImagePostHeadLineByCategoryId(StarPageId starPageId, CategoryId categoryId, long limit) {
        return queryFactory
                .select(
                        Projections.constructor(
                                ImagePostHeadLine.class,
                                starPagePost.id,
                                starPagePost.likeCount,
                                albumPost.image.path
                        )
                )
                .from(starPagePost)
                .innerJoin(category).on(starPagePost.categoryId.eq(category.categoryId))
                .leftJoin(albumPost).on(eqPostId(albumPost.id))
                .leftJoin(starPage).on(starPage.starPageId.eq(category.starPageId))
                .where(
                        eqStarPageId(starPageId),
                        eqCategoryId(categoryId),
                        eqPostStatus(PostStatus.OPEN).or(eqPostStatus(PostStatus.MAIN_EXPOSED))
                )
                .orderBy(starPagePost.createdAt.desc())
                .limit(limit)
                .fetch();
    }

    @Override
    public List<GoldBalancePostHeadLine> searchGoldBalancePostHeadLineByCategoryId(StarPageId starPageId, CategoryId categoryId, long limit) {
        return queryFactory
                .select(
                        Projections.constructor(
                                GoldBalancePostHeadLine.class,
                                starPagePost.id,
                                starPagePost.likeCount,
                                goldBalancePost.question,
                                goldBalancePost.leftDetail.leftExample,
                                goldBalancePost.rightDetail.rightExample,
                                goldBalancePost.leftDetail.leftChooser.size(),
                                goldBalancePost.rightDetail.rightChooser.size(),
                                goldBalancePost.leftRightRate.leftRate,
                                goldBalancePost.leftRightRate.rightRate
                        )
                )
                .from(starPagePost)
                .innerJoin(category).on(starPagePost.categoryId.eq(category.categoryId))
                .innerJoin(goldBalancePost).on(eqPostId(goldBalancePost.id))
                .where(
                        eqStarPageId(starPageId),
                        eqCategoryId(categoryId),
                        eqPostStatus(PostStatus.OPEN).or(eqPostStatus(PostStatus.MAIN_EXPOSED))
                )
                .orderBy(starPagePost.createdAt.desc())
                .limit(limit)
                .fetch();
    }

    @Override
    public List<VotePostHeadLine> searchVotePostHeadLineByCategoryId(StarPageId starPageId, CategoryId categoryId, long limit) {
        return queryFactory
                .select(
                        starPagePost.id,
                        starPagePost.likeCount,
                        votePost.question,
                        voteItem.optionText,
                        voteItem.voterSet.size()
                )
                .from(starPagePost)
                .innerJoin(category).on(starPagePost.categoryId.eq(category.categoryId))
                .innerJoin(votePost).on(eqPostId(votePost.id))
                .innerJoin(votePost.voteItems, voteItem)
                .where(
                        eqStarPageId(starPageId),
                        eqCategoryId(categoryId),
                        eqPostStatus(PostStatus.OPEN).or(eqPostStatus(PostStatus.MAIN_EXPOSED))
                )
                .orderBy(starPagePost.createdAt.desc())
                .limit(limit)
                .transform(GroupBy.groupBy(starPagePost.id)
                        .list(Projections.constructor(
                                VotePostHeadLine.class,
                                starPagePost.id,
                                starPagePost.likeCount,
                                votePost.question,
                                map(voteItem.optionText, voteItem.voterSet.size())
                        )));
    }

    @Override
    public Optional<StarPage> findStarPageWithLayout(StarPageId starPageId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(starPage)
                .innerJoin(starPage.layoutLines, starPageLayoutLine).fetchJoin()
                .where(starPage.starPageId.eq(starPageId))
                .fetchOne());
    }

    @Override
    public Optional<StarPage> findStarPageWithInformation(StarPageId starPageId) {
        return Optional.ofNullable(queryFactory.
                select(starPage)
                .from(starPage)
                .innerJoin(starPage.information.host.starTypes).fetchJoin()
                .innerJoin(starPage.information.host.snsLines).fetchJoin()
                .where(starPage.starPageId.eq(starPageId))
                .fetchOne());
    }


    private BooleanExpression eqStarPageId(StarPageId id) {
        if (id == null || id.getValue().isEmpty()) {
            return null;
        }
        return category.starPageId.eq(id);
    }

    private BooleanExpression eqCategoryId(CategoryId id) {
        if (id == null || id.getValue().isEmpty()) {
            return null;
        }
        return category.categoryId.eq(id);
    }

    private BooleanExpression eqPostId(NumberPath<Long> id) {
        return starPagePost.id.eq(id);
    }

    private BooleanExpression neHostEmail(StringPath email) {
        return starPagePost.author.email.ne(email);
    }

    private BooleanExpression eqHostEmail(StringPath email) {
        return starPagePost.author.email.eq(email);
    }

    private BooleanExpression eqPostStatus(PostStatus status) {
        return starPagePost.status.eq(status);
    }
}

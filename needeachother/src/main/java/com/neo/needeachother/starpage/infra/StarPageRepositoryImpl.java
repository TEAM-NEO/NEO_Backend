package com.neo.needeachother.starpage.infra;

import com.neo.needeachother.post.domain.PostStatus;
import com.neo.needeachother.starpage.domain.StarPageId;
import com.neo.needeachother.starpage.domain.dto.HeadLine;
import com.neo.needeachother.starpage.domain.repository.StarPageRepository;
import com.neo.needeachother.starpage.domain.repository.StarPageRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


import java.util.List;

import static com.neo.needeachother.category.domain.QCategory.category;
import static com.neo.needeachother.post.domain.QAlbumPost.albumPost;
import static com.neo.needeachother.post.domain.QCommonPost.commonPost;
import static com.neo.needeachother.post.domain.QStarPagePost.starPagePost;
import static com.neo.needeachother.starpage.domain.QStarPage.starPage;
import static org.hibernate.internal.util.NullnessHelper.coalesce;

@Repository
@RequiredArgsConstructor
public class StarPageRepositoryImpl implements StarPageRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<HeadLine> searchHeadLineByStarPageIdAndLimit(StarPageId id, long limit) {
        // 카테고리명, 제목, 작성자, 좋아요 수, 글 내용 확인 API Path, 대표 이미지 DTO에 담기.
        List<HeadLine> recentFanPopularPostHeadLines = queryFactory.select(
                        Projections.constructor(
                                HeadLine.class,
                                starPagePost.id, starPagePost.author.authorName, starPagePost.title, starPagePost.postType.stringValue(),
                                category.categoryInformation.categoryTitle,
                                starPagePost.likeCount,
                                albumPost.image.path.coalesce(commonPost.representativeImage).as("representativeImage")
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

        return recentFanPopularPostHeadLines;
    }

    private BooleanExpression eqStarPageId(StarPageId id) {
        if (id == null || id.getValue().isEmpty()) {
            return null;
        }
        return category.starPageId.eq(id);
    }

    private BooleanExpression eqPostId(NumberPath<Long> id) {
        return starPagePost.id.eq(id);
    }

    private BooleanExpression neHostEmail(StringPath email) {
        return starPagePost.author.email.ne(email);
    }

    private BooleanExpression eqPostStatus(PostStatus status) {
        return starPagePost.status.eq(status);
    }
}

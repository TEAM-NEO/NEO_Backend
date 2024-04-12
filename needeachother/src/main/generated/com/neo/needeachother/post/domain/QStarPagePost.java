package com.neo.needeachother.post.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStarPagePost is a Querydsl query type for StarPagePost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStarPagePost extends EntityPathBase<StarPagePost> {

    private static final long serialVersionUID = 888085781L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStarPagePost starPagePost = new QStarPagePost("starPagePost");

    public final com.neo.needeachother.common.entity.QNEOTimeDefaultEntity _super = new com.neo.needeachother.common.entity.QNEOTimeDefaultEntity(this);

    public final QAuthor author;

    public final com.neo.needeachother.category.domain.QCategoryId categoryId;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DateTimePath<java.time.LocalDateTime> exposureAt = createDateTime("exposureAt", java.time.LocalDateTime.class);

    public final BooleanPath hostHeart = createBoolean("hostHeart");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final SetPath<PostLike, QPostLike> likes = this.<PostLike, QPostLike>createSet("likes", PostLike.class, QPostLike.class, PathInits.DIRECT2);

    public final EnumPath<PostType> postType = createEnum("postType", PostType.class);

    public final EnumPath<PostStatus> status = createEnum("status", PostStatus.class);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QStarPagePost(String variable) {
        this(StarPagePost.class, forVariable(variable), INITS);
    }

    public QStarPagePost(Path<? extends StarPagePost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStarPagePost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStarPagePost(PathMetadata metadata, PathInits inits) {
        this(StarPagePost.class, metadata, inits);
    }

    public QStarPagePost(Class<? extends StarPagePost> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.author = inits.isInitialized("author") ? new QAuthor(forProperty("author")) : null;
        this.categoryId = inits.isInitialized("categoryId") ? new com.neo.needeachother.category.domain.QCategoryId(forProperty("categoryId")) : null;
    }

}


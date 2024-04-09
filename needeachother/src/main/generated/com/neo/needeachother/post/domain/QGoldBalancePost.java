package com.neo.needeachother.post.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGoldBalancePost is a Querydsl query type for GoldBalancePost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGoldBalancePost extends EntityPathBase<GoldBalancePost> {

    private static final long serialVersionUID = 1899111368L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGoldBalancePost goldBalancePost = new QGoldBalancePost("goldBalancePost");

    public final QStarPagePost _super;

    // inherited
    public final QAuthor author;

    // inherited
    public final com.neo.needeachother.category.domain.QCategoryId categoryId;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    //inherited
    public final BooleanPath hostHeart;

    //inherited
    public final NumberPath<Long> id;

    public final QGoldBalanceLeftDetail leftDetail;

    public final QLeftRightRate leftRightRate;

    //inherited
    public final NumberPath<Integer> likeCount;

    //inherited
    public final SetPath<PostLike, QPostLike> likes;

    public final StringPath question = createString("question");

    public final QGoldBalanceRightDetail rightDetail;

    //inherited
    public final EnumPath<PostStatus> status;

    //inherited
    public final StringPath title;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt;

    public QGoldBalancePost(String variable) {
        this(GoldBalancePost.class, forVariable(variable), INITS);
    }

    public QGoldBalancePost(Path<? extends GoldBalancePost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGoldBalancePost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGoldBalancePost(PathMetadata metadata, PathInits inits) {
        this(GoldBalancePost.class, metadata, inits);
    }

    public QGoldBalancePost(Class<? extends GoldBalancePost> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QStarPagePost(type, metadata, inits);
        this.author = _super.author;
        this.categoryId = _super.categoryId;
        this.createdAt = _super.createdAt;
        this.hostHeart = _super.hostHeart;
        this.id = _super.id;
        this.leftDetail = inits.isInitialized("leftDetail") ? new QGoldBalanceLeftDetail(forProperty("leftDetail")) : null;
        this.leftRightRate = inits.isInitialized("leftRightRate") ? new QLeftRightRate(forProperty("leftRightRate")) : null;
        this.likeCount = _super.likeCount;
        this.likes = _super.likes;
        this.rightDetail = inits.isInitialized("rightDetail") ? new QGoldBalanceRightDetail(forProperty("rightDetail")) : null;
        this.status = _super.status;
        this.title = _super.title;
        this.updatedAt = _super.updatedAt;
    }

}


package com.neo.needeachother.post.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommonPost is a Querydsl query type for CommonPost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommonPost extends EntityPathBase<CommonPost> {

    private static final long serialVersionUID = -1396702465L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommonPost commonPost = new QCommonPost("commonPost");

    public final QStarPagePost _super;

    // inherited
    public final QAuthor author;

    // inherited
    public final com.neo.needeachother.category.domain.QCategoryId categoryId;

    public final ListPath<CommonPostParagraph, QCommonPostParagraph> commonPostContents = this.<CommonPostParagraph, QCommonPostParagraph>createList("commonPostContents", CommonPostParagraph.class, QCommonPostParagraph.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    //inherited
    public final BooleanPath hostHeart;

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final NumberPath<Integer> likeCount;

    //inherited
    public final SetPath<PostLike, QPostLike> likes;

    //inherited
    public final EnumPath<PostStatus> status;

    //inherited
    public final StringPath title;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt;

    public QCommonPost(String variable) {
        this(CommonPost.class, forVariable(variable), INITS);
    }

    public QCommonPost(Path<? extends CommonPost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommonPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommonPost(PathMetadata metadata, PathInits inits) {
        this(CommonPost.class, metadata, inits);
    }

    public QCommonPost(Class<? extends CommonPost> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QStarPagePost(type, metadata, inits);
        this.author = _super.author;
        this.categoryId = _super.categoryId;
        this.createdAt = _super.createdAt;
        this.hostHeart = _super.hostHeart;
        this.id = _super.id;
        this.likeCount = _super.likeCount;
        this.likes = _super.likes;
        this.status = _super.status;
        this.title = _super.title;
        this.updatedAt = _super.updatedAt;
    }

}


package com.neo.needeachother.post.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAlbumPost is a Querydsl query type for AlbumPost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlbumPost extends EntityPathBase<AlbumPost> {

    private static final long serialVersionUID = 581728091L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAlbumPost albumPost = new QAlbumPost("albumPost");

    public final QStarPagePost _super;

    // inherited
    public final QAuthor author;

    // inherited
    public final com.neo.needeachother.category.domain.QCategoryId categoryId;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> exposureAt;

    //inherited
    public final BooleanPath hostHeart;

    //inherited
    public final NumberPath<Long> id;

    public final QAlbumImage image;

    //inherited
    public final NumberPath<Integer> likeCount;

    //inherited
    public final SetPath<PostLike, QPostLike> likes;

    //inherited
    public final EnumPath<PostType> postType;

    //inherited
    public final EnumPath<PostStatus> status;

    //inherited
    public final StringPath title;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt;

    public QAlbumPost(String variable) {
        this(AlbumPost.class, forVariable(variable), INITS);
    }

    public QAlbumPost(Path<? extends AlbumPost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAlbumPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAlbumPost(PathMetadata metadata, PathInits inits) {
        this(AlbumPost.class, metadata, inits);
    }

    public QAlbumPost(Class<? extends AlbumPost> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QStarPagePost(type, metadata, inits);
        this.author = _super.author;
        this.categoryId = _super.categoryId;
        this.createdAt = _super.createdAt;
        this.exposureAt = _super.exposureAt;
        this.hostHeart = _super.hostHeart;
        this.id = _super.id;
        this.image = inits.isInitialized("image") ? new QAlbumImage(forProperty("image")) : null;
        this.likeCount = _super.likeCount;
        this.likes = _super.likes;
        this.postType = _super.postType;
        this.status = _super.status;
        this.title = _super.title;
        this.updatedAt = _super.updatedAt;
    }

}


package com.neo.needeachother.post.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVotePost is a Querydsl query type for VotePost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVotePost extends EntityPathBase<VotePost> {

    private static final long serialVersionUID = -537265794L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVotePost votePost = new QVotePost("votePost");

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

    //inherited
    public final NumberPath<Integer> likeCount;

    //inherited
    public final SetPath<PostLike, QPostLike> likes;

    //inherited
    public final EnumPath<PostType> postType;

    public final StringPath question = createString("question");

    //inherited
    public final EnumPath<PostStatus> status;

    public final NumberPath<Integer> timeToLive = createNumber("timeToLive", Integer.class);

    //inherited
    public final StringPath title;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt;

    public final ListPath<VoteItem, QVoteItem> voteItems = this.<VoteItem, QVoteItem>createList("voteItems", VoteItem.class, QVoteItem.class, PathInits.DIRECT2);

    public final EnumPath<VoteStatus> voteStatus = createEnum("voteStatus", VoteStatus.class);

    public QVotePost(String variable) {
        this(VotePost.class, forVariable(variable), INITS);
    }

    public QVotePost(Path<? extends VotePost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVotePost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVotePost(PathMetadata metadata, PathInits inits) {
        this(VotePost.class, metadata, inits);
    }

    public QVotePost(Class<? extends VotePost> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QStarPagePost(type, metadata, inits);
        this.author = _super.author;
        this.categoryId = _super.categoryId;
        this.createdAt = _super.createdAt;
        this.exposureAt = _super.exposureAt;
        this.hostHeart = _super.hostHeart;
        this.id = _super.id;
        this.likeCount = _super.likeCount;
        this.likes = _super.likes;
        this.postType = _super.postType;
        this.status = _super.status;
        this.title = _super.title;
        this.updatedAt = _super.updatedAt;
    }

}


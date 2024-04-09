package com.neo.needeachother.post.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPostLike is a Querydsl query type for PostLike
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QPostLike extends BeanPath<PostLike> {

    private static final long serialVersionUID = 1875872555L;

    public static final QPostLike postLike = new QPostLike("postLike");

    public final StringPath likerEmail = createString("likerEmail");

    public QPostLike(String variable) {
        super(PostLike.class, forVariable(variable));
    }

    public QPostLike(Path<? extends PostLike> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPostLike(PathMetadata metadata) {
        super(PostLike.class, metadata);
    }

}


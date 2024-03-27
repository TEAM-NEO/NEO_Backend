package com.neo.needeachother.starpage.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QStarPageId is a Querydsl query type for StarPageId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QStarPageId extends BeanPath<StarPageId> {

    private static final long serialVersionUID = 862767057L;

    public static final QStarPageId starPageId = new QStarPageId("starPageId");

    public final StringPath value = createString("value");

    public QStarPageId(String variable) {
        super(StarPageId.class, forVariable(variable));
    }

    public QStarPageId(Path<? extends StarPageId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStarPageId(PathMetadata metadata) {
        super(StarPageId.class, metadata);
    }

}


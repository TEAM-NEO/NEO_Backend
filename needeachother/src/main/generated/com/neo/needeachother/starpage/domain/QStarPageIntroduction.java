package com.neo.needeachother.starpage.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QStarPageIntroduction is a Querydsl query type for StarPageIntroduction
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QStarPageIntroduction extends BeanPath<StarPageIntroduction> {

    private static final long serialVersionUID = 1017620304L;

    public static final QStarPageIntroduction starPageIntroduction = new QStarPageIntroduction("starPageIntroduction");

    public final StringPath value = createString("value");

    public QStarPageIntroduction(String variable) {
        super(StarPageIntroduction.class, forVariable(variable));
    }

    public QStarPageIntroduction(Path<? extends StarPageIntroduction> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStarPageIntroduction(PathMetadata metadata) {
        super(StarPageIntroduction.class, metadata);
    }

}


package com.neo.needeachother.post.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLeftRightRate is a Querydsl query type for LeftRightRate
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QLeftRightRate extends BeanPath<LeftRightRate> {

    private static final long serialVersionUID = -1348488575L;

    public static final QLeftRightRate leftRightRate = new QLeftRightRate("leftRightRate");

    public final NumberPath<Integer> leftRate = createNumber("leftRate", Integer.class);

    public final NumberPath<Integer> rightRate = createNumber("rightRate", Integer.class);

    public QLeftRightRate(String variable) {
        super(LeftRightRate.class, forVariable(variable));
    }

    public QLeftRightRate(Path<? extends LeftRightRate> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLeftRightRate(PathMetadata metadata) {
        super(LeftRightRate.class, metadata);
    }

}


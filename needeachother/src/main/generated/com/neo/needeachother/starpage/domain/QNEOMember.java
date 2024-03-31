package com.neo.needeachother.starpage.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNEOMember is a Querydsl query type for NEOMember
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QNEOMember extends BeanPath<NEOMember> {

    private static final long serialVersionUID = -914193187L;

    public static final QNEOMember nEOMember = new QNEOMember("nEOMember");

    public final StringPath email = createString("email");

    public QNEOMember(String variable) {
        super(NEOMember.class, forVariable(variable));
    }

    public QNEOMember(Path<? extends NEOMember> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNEOMember(PathMetadata metadata) {
        super(NEOMember.class, metadata);
    }

}


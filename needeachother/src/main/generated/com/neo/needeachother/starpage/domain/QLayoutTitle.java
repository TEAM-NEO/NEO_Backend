package com.neo.needeachother.starpage.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLayoutTitle is a Querydsl query type for LayoutTitle
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QLayoutTitle extends BeanPath<LayoutTitle> {

    private static final long serialVersionUID = 2045256985L;

    public static final QLayoutTitle layoutTitle = new QLayoutTitle("layoutTitle");

    public final StringPath value = createString("value");

    public QLayoutTitle(String variable) {
        super(LayoutTitle.class, forVariable(variable));
    }

    public QLayoutTitle(Path<? extends LayoutTitle> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLayoutTitle(PathMetadata metadata) {
        super(LayoutTitle.class, metadata);
    }

}


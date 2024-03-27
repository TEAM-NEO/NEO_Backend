package com.neo.needeachother.starpage.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStarPageHost is a Querydsl query type for StarPageHost
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QStarPageHost extends BeanPath<StarPageHost> {

    private static final long serialVersionUID = 190438110L;

    public static final QStarPageHost starPageHost = new QStarPageHost("starPageHost");

    public final StringPath email = createString("email");

    public final ListPath<SNSLine, QSNSLine> snsLines = this.<SNSLine, QSNSLine>createList("snsLines", SNSLine.class, QSNSLine.class, PathInits.DIRECT2);

    public final StringPath starNickName = createString("starNickName");

    public final SetPath<StarType, EnumPath<StarType>> starTypes = this.<StarType, EnumPath<StarType>>createSet("starTypes", StarType.class, EnumPath.class, PathInits.DIRECT2);

    public QStarPageHost(String variable) {
        super(StarPageHost.class, forVariable(variable));
    }

    public QStarPageHost(Path<? extends StarPageHost> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStarPageHost(PathMetadata metadata) {
        super(StarPageHost.class, metadata);
    }

}


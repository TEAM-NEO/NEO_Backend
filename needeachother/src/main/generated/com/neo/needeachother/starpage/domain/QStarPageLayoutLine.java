package com.neo.needeachother.starpage.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStarPageLayoutLine is a Querydsl query type for StarPageLayoutLine
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStarPageLayoutLine extends EntityPathBase<StarPageLayoutLine> {

    private static final long serialVersionUID = 1024663284L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStarPageLayoutLine starPageLayoutLine = new QStarPageLayoutLine("starPageLayoutLine");

    public final NumberPath<Long> layoutId = createNumber("layoutId", Long.class);

    public final QLayoutTitle layoutTitle;

    public final EnumPath<StarPageLayoutType> type = createEnum("type", StarPageLayoutType.class);

    public QStarPageLayoutLine(String variable) {
        this(StarPageLayoutLine.class, forVariable(variable), INITS);
    }

    public QStarPageLayoutLine(Path<? extends StarPageLayoutLine> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStarPageLayoutLine(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStarPageLayoutLine(PathMetadata metadata, PathInits inits) {
        this(StarPageLayoutLine.class, metadata, inits);
    }

    public QStarPageLayoutLine(Class<? extends StarPageLayoutLine> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.layoutTitle = inits.isInitialized("layoutTitle") ? new QLayoutTitle(forProperty("layoutTitle")) : null;
    }

}


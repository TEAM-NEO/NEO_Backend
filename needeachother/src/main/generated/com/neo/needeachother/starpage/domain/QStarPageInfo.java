package com.neo.needeachother.starpage.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStarPageInfo is a Querydsl query type for StarPageInfo
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QStarPageInfo extends BeanPath<StarPageInfo> {

    private static final long serialVersionUID = 190466532L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStarPageInfo starPageInfo = new QStarPageInfo("starPageInfo");

    public final QStarPageHost host;

    public final QStarPageIntroduction introduction;

    public final QImage profileImage;

    public final QImage topRepresentativeImage;

    public QStarPageInfo(String variable) {
        this(StarPageInfo.class, forVariable(variable), INITS);
    }

    public QStarPageInfo(Path<? extends StarPageInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStarPageInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStarPageInfo(PathMetadata metadata, PathInits inits) {
        this(StarPageInfo.class, metadata, inits);
    }

    public QStarPageInfo(Class<? extends StarPageInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.host = inits.isInitialized("host") ? new QStarPageHost(forProperty("host")) : null;
        this.introduction = inits.isInitialized("introduction") ? new QStarPageIntroduction(forProperty("introduction")) : null;
        this.profileImage = inits.isInitialized("profileImage") ? new QImage(forProperty("profileImage")) : null;
        this.topRepresentativeImage = inits.isInitialized("topRepresentativeImage") ? new QImage(forProperty("topRepresentativeImage")) : null;
    }

}


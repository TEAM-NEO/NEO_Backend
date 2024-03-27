package com.neo.needeachother.starpage.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTopRepresentativeImage is a Querydsl query type for TopRepresentativeImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTopRepresentativeImage extends EntityPathBase<TopRepresentativeImage> {

    private static final long serialVersionUID = -468112002L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTopRepresentativeImage topRepresentativeImage = new QTopRepresentativeImage("topRepresentativeImage");

    public final QImage _super;

    //inherited
    public final NumberPath<Long> id;

    // inherited
    public final QSavedImageURL url;

    public QTopRepresentativeImage(String variable) {
        this(TopRepresentativeImage.class, forVariable(variable), INITS);
    }

    public QTopRepresentativeImage(Path<? extends TopRepresentativeImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTopRepresentativeImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTopRepresentativeImage(PathMetadata metadata, PathInits inits) {
        this(TopRepresentativeImage.class, metadata, inits);
    }

    public QTopRepresentativeImage(Class<? extends TopRepresentativeImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QImage(type, metadata, inits);
        this.id = _super.id;
        this.url = _super.url;
    }

}


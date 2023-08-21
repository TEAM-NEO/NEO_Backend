package com.neo.needeachother.users.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNEOStarTypeEntity is a Querydsl query type for NEOStarTypeEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNEOStarTypeEntity extends EntityPathBase<NEOStarTypeEntity> {

    private static final long serialVersionUID = -533211334L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNEOStarTypeEntity nEOStarTypeEntity = new QNEOStarTypeEntity("nEOStarTypeEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QNEOStarEntity neoStar;

    public final EnumPath<com.neo.needeachother.users.enums.NEOStarDetailClassification> starType = createEnum("starType", com.neo.needeachother.users.enums.NEOStarDetailClassification.class);

    public QNEOStarTypeEntity(String variable) {
        this(NEOStarTypeEntity.class, forVariable(variable), INITS);
    }

    public QNEOStarTypeEntity(Path<? extends NEOStarTypeEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNEOStarTypeEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNEOStarTypeEntity(PathMetadata metadata, PathInits inits) {
        this(NEOStarTypeEntity.class, metadata, inits);
    }

    public QNEOStarTypeEntity(Class<? extends NEOStarTypeEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.neoStar = inits.isInitialized("neoStar") ? new QNEOStarEntity(forProperty("neoStar")) : null;
    }

}


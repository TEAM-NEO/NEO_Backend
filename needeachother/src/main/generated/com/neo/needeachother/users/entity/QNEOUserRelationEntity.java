package com.neo.needeachother.users.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNEOUserRelationEntity is a Querydsl query type for NEOUserRelationEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNEOUserRelationEntity extends EntityPathBase<NEOUserRelationEntity> {

    private static final long serialVersionUID = 1354497973L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNEOUserRelationEntity nEOUserRelationEntity = new QNEOUserRelationEntity("nEOUserRelationEntity");

    public final QNEOStarEntity followee;

    public final QNEOUserEntity follower;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QNEOUserRelationEntity(String variable) {
        this(NEOUserRelationEntity.class, forVariable(variable), INITS);
    }

    public QNEOUserRelationEntity(Path<? extends NEOUserRelationEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNEOUserRelationEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNEOUserRelationEntity(PathMetadata metadata, PathInits inits) {
        this(NEOUserRelationEntity.class, metadata, inits);
    }

    public QNEOUserRelationEntity(Class<? extends NEOUserRelationEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.followee = inits.isInitialized("followee") ? new QNEOStarEntity(forProperty("followee")) : null;
        this.follower = inits.isInitialized("follower") ? new QNEOUserEntity(forProperty("follower")) : null;
    }

}


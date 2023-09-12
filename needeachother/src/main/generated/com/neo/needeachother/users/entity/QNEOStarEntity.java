package com.neo.needeachother.users.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNEOStarEntity is a Querydsl query type for NEOStarEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNEOStarEntity extends EntityPathBase<NEOStarEntity> {

    private static final long serialVersionUID = -836930080L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNEOStarEntity nEOStarEntity = new QNEOStarEntity("nEOStarEntity");

    public final ListPath<NEOUserRelationEntity, QNEOUserRelationEntity> followerList = this.<NEOUserRelationEntity, QNEOUserRelationEntity>createList("followerList", NEOUserRelationEntity.class, QNEOUserRelationEntity.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath starNickName = createString("starNickName");

    public final ListPath<NEOStarTypeEntity, QNEOStarTypeEntity> starTypeList = this.<NEOStarTypeEntity, QNEOStarTypeEntity>createList("starTypeList", NEOStarTypeEntity.class, QNEOStarTypeEntity.class, PathInits.DIRECT2);

    public final QNEOUserEntity userInformation;

    public QNEOStarEntity(String variable) {
        this(NEOStarEntity.class, forVariable(variable), INITS);
    }

    public QNEOStarEntity(Path<? extends NEOStarEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNEOStarEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNEOStarEntity(PathMetadata metadata, PathInits inits) {
        this(NEOStarEntity.class, metadata, inits);
    }

    public QNEOStarEntity(Class<? extends NEOStarEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.userInformation = inits.isInitialized("userInformation") ? new QNEOUserEntity(forProperty("userInformation"), inits.get("userInformation")) : null;
    }

}


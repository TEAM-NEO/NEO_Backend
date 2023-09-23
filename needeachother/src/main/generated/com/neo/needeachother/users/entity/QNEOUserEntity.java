package com.neo.needeachother.users.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNEOUserEntity is a Querydsl query type for NEOUserEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNEOUserEntity extends EntityPathBase<NEOUserEntity> {

    private static final long serialVersionUID = -1041195879L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNEOUserEntity nEOUserEntity = new QNEOUserEntity("nEOUserEntity");

    public final com.neo.needeachother.common.entity.QNEOTimeDefaultEntity _super = new com.neo.needeachother.common.entity.QNEOTimeDefaultEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final EnumPath<com.neo.needeachother.users.enums.NEOGenderType> gender = createEnum("gender", com.neo.needeachother.users.enums.NEOGenderType.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath neoID = createString("neoID");

    public final StringPath neoNickName = createString("neoNickName");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final EnumPath<com.neo.needeachother.auth.enums.NEOOAuth2ProviderType> providerType = createEnum("providerType", com.neo.needeachother.auth.enums.NEOOAuth2ProviderType.class);

    public final StringPath socialID = createString("socialID");

    public final QNEOStarEntity starInformation;

    public final ListPath<NEOUserRelationEntity, QNEOUserRelationEntity> subscribedStarList = this.<NEOUserRelationEntity, QNEOUserRelationEntity>createList("subscribedStarList", NEOUserRelationEntity.class, QNEOUserRelationEntity.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final StringPath userName = createString("userName");

    public final StringPath userPW = createString("userPW");

    public final EnumPath<com.neo.needeachother.users.enums.NEOUserType> userType = createEnum("userType", com.neo.needeachother.users.enums.NEOUserType.class);

    public QNEOUserEntity(String variable) {
        this(NEOUserEntity.class, forVariable(variable), INITS);
    }

    public QNEOUserEntity(Path<? extends NEOUserEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNEOUserEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNEOUserEntity(PathMetadata metadata, PathInits inits) {
        this(NEOUserEntity.class, metadata, inits);
    }

    public QNEOUserEntity(Class<? extends NEOUserEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.starInformation = inits.isInitialized("starInformation") ? new QNEOStarEntity(forProperty("starInformation"), inits.get("starInformation")) : null;
    }

}


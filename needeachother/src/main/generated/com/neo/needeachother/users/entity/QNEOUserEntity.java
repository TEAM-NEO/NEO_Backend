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

    public static final QNEOUserEntity nEOUserEntity = new QNEOUserEntity("nEOUserEntity");

    public final com.neo.needeachother.common.entity.QNEOTimeDefaultEntity _super = new com.neo.needeachother.common.entity.QNEOTimeDefaultEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final EnumPath<com.neo.needeachother.users.enums.NEOGenderType> gender = createEnum("gender", com.neo.needeachother.users.enums.NEOGenderType.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath neoNickName = createString("neoNickName");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final StringPath providerType = createString("providerType");

    public final ListPath<NEOUserRelationEntity, QNEOUserRelationEntity> subscribedStarList = this.<NEOUserRelationEntity, QNEOUserRelationEntity>createList("subscribedStarList", NEOUserRelationEntity.class, QNEOUserRelationEntity.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final StringPath userID = createString("userID");

    public final StringPath userName = createString("userName");

    public final StringPath userPW = createString("userPW");

    public QNEOUserEntity(String variable) {
        super(NEOUserEntity.class, forVariable(variable));
    }

    public QNEOUserEntity(Path<? extends NEOUserEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNEOUserEntity(PathMetadata metadata) {
        super(NEOUserEntity.class, metadata);
    }

}


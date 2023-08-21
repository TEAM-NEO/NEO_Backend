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

    public static final QNEOStarEntity nEOStarEntity = new QNEOStarEntity("nEOStarEntity");

    public final QNEOUserEntity _super = new QNEOUserEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath email = _super.email;

    public final ListPath<NEOUserRelationEntity, QNEOUserRelationEntity> followerList = this.<NEOUserRelationEntity, QNEOUserRelationEntity>createList("followerList", NEOUserRelationEntity.class, QNEOUserRelationEntity.class, PathInits.DIRECT2);

    //inherited
    public final EnumPath<com.neo.needeachother.users.enums.NEOGenderType> gender = _super.gender;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final StringPath neoNickName = _super.neoNickName;

    //inherited
    public final StringPath phoneNumber = _super.phoneNumber;

    //inherited
    public final StringPath providerType = _super.providerType;

    public final StringPath starNickName = createString("starNickName");

    public final ListPath<NEOStarTypeEntity, QNEOStarTypeEntity> starTypeList = this.<NEOStarTypeEntity, QNEOStarTypeEntity>createList("starTypeList", NEOStarTypeEntity.class, QNEOStarTypeEntity.class, PathInits.DIRECT2);

    //inherited
    public final ListPath<NEOUserRelationEntity, QNEOUserRelationEntity> subscribedStarList = _super.subscribedStarList;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final StringPath userID = _super.userID;

    //inherited
    public final StringPath userName = _super.userName;

    //inherited
    public final StringPath userPW = _super.userPW;

    public QNEOStarEntity(String variable) {
        super(NEOStarEntity.class, forVariable(variable));
    }

    public QNEOStarEntity(Path<? extends NEOStarEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNEOStarEntity(PathMetadata metadata) {
        super(NEOStarEntity.class, metadata);
    }

}


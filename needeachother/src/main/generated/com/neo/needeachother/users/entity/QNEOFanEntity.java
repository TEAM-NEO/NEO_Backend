package com.neo.needeachother.users.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNEOFanEntity is a Querydsl query type for NEOFanEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNEOFanEntity extends EntityPathBase<NEOFanEntity> {

    private static final long serialVersionUID = -2127163797L;

    public static final QNEOFanEntity nEOFanEntity = new QNEOFanEntity("nEOFanEntity");

    public final QNEOUserEntity _super = new QNEOUserEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath email = _super.email;

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

    public final EnumPath<com.neo.needeachother.users.enums.NEOUserType> userType = createEnum("userType", com.neo.needeachother.users.enums.NEOUserType.class);

    public QNEOFanEntity(String variable) {
        super(NEOFanEntity.class, forVariable(variable));
    }

    public QNEOFanEntity(Path<? extends NEOFanEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNEOFanEntity(PathMetadata metadata) {
        super(NEOFanEntity.class, metadata);
    }

}


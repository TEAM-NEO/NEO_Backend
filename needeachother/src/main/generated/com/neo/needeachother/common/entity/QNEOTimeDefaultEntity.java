package com.neo.needeachother.common.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNEOTimeDefaultEntity is a Querydsl query type for NEOTimeDefaultEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QNEOTimeDefaultEntity extends EntityPathBase<NEOTimeDefaultEntity> {

    private static final long serialVersionUID = -1202157345L;

    public static final QNEOTimeDefaultEntity nEOTimeDefaultEntity = new QNEOTimeDefaultEntity("nEOTimeDefaultEntity");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QNEOTimeDefaultEntity(String variable) {
        super(NEOTimeDefaultEntity.class, forVariable(variable));
    }

    public QNEOTimeDefaultEntity(Path<? extends NEOTimeDefaultEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNEOTimeDefaultEntity(PathMetadata metadata) {
        super(NEOTimeDefaultEntity.class, metadata);
    }

}


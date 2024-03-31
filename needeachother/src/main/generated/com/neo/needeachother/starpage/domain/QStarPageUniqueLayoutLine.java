package com.neo.needeachother.starpage.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStarPageUniqueLayoutLine is a Querydsl query type for StarPageUniqueLayoutLine
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStarPageUniqueLayoutLine extends EntityPathBase<StarPageUniqueLayoutLine> {

    private static final long serialVersionUID = -2131569371L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStarPageUniqueLayoutLine starPageUniqueLayoutLine = new QStarPageUniqueLayoutLine("starPageUniqueLayoutLine");

    public final QStarPageLayoutLine _super;

    //inherited
    public final NumberPath<Long> layoutId;

    // inherited
    public final QLayoutTitle layoutTitle;

    public final BooleanPath removeAble = createBoolean("removeAble");

    //inherited
    public final EnumPath<StarPageLayoutType> type;

    public QStarPageUniqueLayoutLine(String variable) {
        this(StarPageUniqueLayoutLine.class, forVariable(variable), INITS);
    }

    public QStarPageUniqueLayoutLine(Path<? extends StarPageUniqueLayoutLine> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStarPageUniqueLayoutLine(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStarPageUniqueLayoutLine(PathMetadata metadata, PathInits inits) {
        this(StarPageUniqueLayoutLine.class, metadata, inits);
    }

    public QStarPageUniqueLayoutLine(Class<? extends StarPageUniqueLayoutLine> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QStarPageLayoutLine(type, metadata, inits);
        this.layoutId = _super.layoutId;
        this.layoutTitle = _super.layoutTitle;
        this.type = _super.type;
    }

}


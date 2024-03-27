package com.neo.needeachother.starpage.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCategoricalLayoutLine is a Querydsl query type for CategoricalLayoutLine
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCategoricalLayoutLine extends EntityPathBase<CategoricalLayoutLine> {

    private static final long serialVersionUID = -715807607L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCategoricalLayoutLine categoricalLayoutLine = new QCategoricalLayoutLine("categoricalLayoutLine");

    public final QStarPageLayoutLine _super;

    public final QCategoryId categoryId;

    //inherited
    public final NumberPath<Long> layoutId;

    // inherited
    public final QLayoutTitle layoutTitle;

    //inherited
    public final EnumPath<StarPageLayoutType> type;

    public QCategoricalLayoutLine(String variable) {
        this(CategoricalLayoutLine.class, forVariable(variable), INITS);
    }

    public QCategoricalLayoutLine(Path<? extends CategoricalLayoutLine> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCategoricalLayoutLine(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCategoricalLayoutLine(PathMetadata metadata, PathInits inits) {
        this(CategoricalLayoutLine.class, metadata, inits);
    }

    public QCategoricalLayoutLine(Class<? extends CategoricalLayoutLine> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QStarPageLayoutLine(type, metadata, inits);
        this.categoryId = inits.isInitialized("categoryId") ? new QCategoryId(forProperty("categoryId")) : null;
        this.layoutId = _super.layoutId;
        this.layoutTitle = _super.layoutTitle;
        this.type = _super.type;
    }

}


package com.neo.needeachother.category.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCategory is a Querydsl query type for Category
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCategory extends EntityPathBase<Category> {

    private static final long serialVersionUID = 1622784048L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCategory category = new QCategory("category");

    public final QCategoryId categoryId;

    public final QCategoryInformation categoryInformation;

    public final EnumPath<CategoryStatus> categoryStatus = createEnum("categoryStatus", CategoryStatus.class);

    public final EnumPath<ContentType> contentType = createEnum("contentType", ContentType.class);

    public final QContentRestriction restriction;

    public final com.neo.needeachother.starpage.domain.QStarPageId starPageId;

    public QCategory(String variable) {
        this(Category.class, forVariable(variable), INITS);
    }

    public QCategory(Path<? extends Category> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCategory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCategory(PathMetadata metadata, PathInits inits) {
        this(Category.class, metadata, inits);
    }

    public QCategory(Class<? extends Category> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.categoryId = inits.isInitialized("categoryId") ? new QCategoryId(forProperty("categoryId")) : null;
        this.categoryInformation = inits.isInitialized("categoryInformation") ? new QCategoryInformation(forProperty("categoryInformation")) : null;
        this.restriction = inits.isInitialized("restriction") ? new QContentRestriction(forProperty("restriction")) : null;
        this.starPageId = inits.isInitialized("starPageId") ? new com.neo.needeachother.starpage.domain.QStarPageId(forProperty("starPageId")) : null;
    }

}


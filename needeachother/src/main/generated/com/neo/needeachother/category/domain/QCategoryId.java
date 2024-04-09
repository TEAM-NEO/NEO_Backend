package com.neo.needeachother.category.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCategoryId is a Querydsl query type for CategoryId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QCategoryId extends BeanPath<CategoryId> {

    private static final long serialVersionUID = 422344043L;

    public static final QCategoryId categoryId = new QCategoryId("categoryId");

    public final StringPath value = createString("value");

    public QCategoryId(String variable) {
        super(CategoryId.class, forVariable(variable));
    }

    public QCategoryId(Path<? extends CategoryId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCategoryId(PathMetadata metadata) {
        super(CategoryId.class, metadata);
    }

}


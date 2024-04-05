package com.neo.needeachother.category.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCategoryInformation is a Querydsl query type for CategoryInformation
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QCategoryInformation extends BeanPath<CategoryInformation> {

    private static final long serialVersionUID = 445802204L;

    public static final QCategoryInformation categoryInformation = new QCategoryInformation("categoryInformation");

    public final StringPath categoryTitle = createString("categoryTitle");

    public QCategoryInformation(String variable) {
        super(CategoryInformation.class, forVariable(variable));
    }

    public QCategoryInformation(Path<? extends CategoryInformation> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCategoryInformation(PathMetadata metadata) {
        super(CategoryInformation.class, metadata);
    }

}


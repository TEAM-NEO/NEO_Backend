package com.neo.needeachother.category.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QContentRestriction is a Querydsl query type for ContentRestriction
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QContentRestriction extends BeanPath<ContentRestriction> {

    private static final long serialVersionUID = 1073305157L;

    public static final QContentRestriction contentRestriction = new QContentRestriction("contentRestriction");

    public final NumberPath<Integer> filteringRate = createNumber("filteringRate", Integer.class);

    public final BooleanPath isWriteAbleComment = createBoolean("isWriteAbleComment");

    public final BooleanPath onlyHostWriteContent = createBoolean("onlyHostWriteContent");

    public final BooleanPath useCommentRatingFilter = createBoolean("useCommentRatingFilter");

    public final BooleanPath writeAbleComment = createBoolean("writeAbleComment");

    public QContentRestriction(String variable) {
        super(ContentRestriction.class, forVariable(variable));
    }

    public QContentRestriction(Path<? extends ContentRestriction> path) {
        super(path.getType(), path.getMetadata());
    }

    public QContentRestriction(PathMetadata metadata) {
        super(ContentRestriction.class, metadata);
    }

}


package com.neo.needeachother.starpage.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSavedImageURL is a Querydsl query type for SavedImageURL
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QSavedImageURL extends BeanPath<SavedImageURL> {

    private static final long serialVersionUID = -1388808506L;

    public static final QSavedImageURL savedImageURL = new QSavedImageURL("savedImageURL");

    public final StringPath value = createString("value");

    public QSavedImageURL(String variable) {
        super(SavedImageURL.class, forVariable(variable));
    }

    public QSavedImageURL(Path<? extends SavedImageURL> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSavedImageURL(PathMetadata metadata) {
        super(SavedImageURL.class, metadata);
    }

}


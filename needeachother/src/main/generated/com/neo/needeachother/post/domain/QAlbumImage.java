package com.neo.needeachother.post.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAlbumImage is a Querydsl query type for AlbumImage
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QAlbumImage extends BeanPath<AlbumImage> {

    private static final long serialVersionUID = 847159808L;

    public static final QAlbumImage albumImage = new QAlbumImage("albumImage");

    public final StringPath path = createString("path");

    public QAlbumImage(String variable) {
        super(AlbumImage.class, forVariable(variable));
    }

    public QAlbumImage(Path<? extends AlbumImage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAlbumImage(PathMetadata metadata) {
        super(AlbumImage.class, metadata);
    }

}


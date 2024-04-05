package com.neo.needeachother.post.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCommonPostParagraph is a Querydsl query type for CommonPostParagraph
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QCommonPostParagraph extends BeanPath<CommonPostParagraph> {

    private static final long serialVersionUID = 2129384751L;

    public static final QCommonPostParagraph commonPostParagraph = new QCommonPostParagraph("commonPostParagraph");

    public final StringPath body = createString("body");

    public final EnumPath<CommonPostParagraphType> commonPostContentType = createEnum("commonPostContentType", CommonPostParagraphType.class);

    public QCommonPostParagraph(String variable) {
        super(CommonPostParagraph.class, forVariable(variable));
    }

    public QCommonPostParagraph(Path<? extends CommonPostParagraph> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCommonPostParagraph(PathMetadata metadata) {
        super(CommonPostParagraph.class, metadata);
    }

}


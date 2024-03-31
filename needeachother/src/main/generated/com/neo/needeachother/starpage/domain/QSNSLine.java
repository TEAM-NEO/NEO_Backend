package com.neo.needeachother.starpage.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSNSLine is a Querydsl query type for SNSLine
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QSNSLine extends BeanPath<SNSLine> {

    private static final long serialVersionUID = 523600599L;

    public static final QSNSLine sNSLine = new QSNSLine("sNSLine");

    public final EnumPath<SNSType> type = createEnum("type", SNSType.class);

    public final StringPath url = createString("url");

    public QSNSLine(String variable) {
        super(SNSLine.class, forVariable(variable));
    }

    public QSNSLine(Path<? extends SNSLine> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSNSLine(PathMetadata metadata) {
        super(SNSLine.class, metadata);
    }

}


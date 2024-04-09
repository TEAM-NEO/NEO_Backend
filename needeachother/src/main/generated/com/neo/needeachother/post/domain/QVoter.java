package com.neo.needeachother.post.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QVoter is a Querydsl query type for Voter
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QVoter extends BeanPath<Voter> {

    private static final long serialVersionUID = -1800700812L;

    public static final QVoter voter = new QVoter("voter");

    public final StringPath email = createString("email");

    public QVoter(String variable) {
        super(Voter.class, forVariable(variable));
    }

    public QVoter(Path<? extends Voter> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVoter(PathMetadata metadata) {
        super(Voter.class, metadata);
    }

}


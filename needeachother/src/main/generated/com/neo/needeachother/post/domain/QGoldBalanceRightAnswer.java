package com.neo.needeachother.post.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QGoldBalanceRightAnswer is a Querydsl query type for GoldBalanceRightAnswer
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QGoldBalanceRightAnswer extends BeanPath<GoldBalanceRightAnswer> {

    private static final long serialVersionUID = 795541714L;

    public static final QGoldBalanceRightAnswer goldBalanceRightAnswer = new QGoldBalanceRightAnswer("goldBalanceRightAnswer");

    public final StringPath email = createString("email");

    public QGoldBalanceRightAnswer(String variable) {
        super(GoldBalanceRightAnswer.class, forVariable(variable));
    }

    public QGoldBalanceRightAnswer(Path<? extends GoldBalanceRightAnswer> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGoldBalanceRightAnswer(PathMetadata metadata) {
        super(GoldBalanceRightAnswer.class, metadata);
    }

}


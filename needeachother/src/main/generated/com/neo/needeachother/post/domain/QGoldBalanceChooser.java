package com.neo.needeachother.post.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QGoldBalanceChooser is a Querydsl query type for GoldBalanceChooser
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QGoldBalanceChooser extends BeanPath<GoldBalanceChooser> {

    private static final long serialVersionUID = -34205421L;

    public static final QGoldBalanceChooser goldBalanceChooser = new QGoldBalanceChooser("goldBalanceChooser");

    public final StringPath email = createString("email");

    public QGoldBalanceChooser(String variable) {
        super(GoldBalanceChooser.class, forVariable(variable));
    }

    public QGoldBalanceChooser(Path<? extends GoldBalanceChooser> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGoldBalanceChooser(PathMetadata metadata) {
        super(GoldBalanceChooser.class, metadata);
    }

}


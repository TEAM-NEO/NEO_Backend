package com.neo.needeachother.post.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGoldBalanceRightDetail is a Querydsl query type for GoldBalanceRightDetail
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QGoldBalanceRightDetail extends BeanPath<GoldBalanceRightDetail> {

    private static final long serialVersionUID = 873126245L;

    public static final QGoldBalanceRightDetail goldBalanceRightDetail = new QGoldBalanceRightDetail("goldBalanceRightDetail");

    public final SetPath<GoldBalanceChooser, QGoldBalanceChooser> rightChooser = this.<GoldBalanceChooser, QGoldBalanceChooser>createSet("rightChooser", GoldBalanceChooser.class, QGoldBalanceChooser.class, PathInits.DIRECT2);

    public final StringPath rightExample = createString("rightExample");

    public QGoldBalanceRightDetail(String variable) {
        super(GoldBalanceRightDetail.class, forVariable(variable));
    }

    public QGoldBalanceRightDetail(Path<? extends GoldBalanceRightDetail> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGoldBalanceRightDetail(PathMetadata metadata) {
        super(GoldBalanceRightDetail.class, metadata);
    }

}


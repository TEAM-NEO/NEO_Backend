package com.neo.needeachother.post.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGoldBalanceLeftDetail is a Querydsl query type for GoldBalanceLeftDetail
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QGoldBalanceLeftDetail extends BeanPath<GoldBalanceLeftDetail> {

    private static final long serialVersionUID = -1455959968L;

    public static final QGoldBalanceLeftDetail goldBalanceLeftDetail = new QGoldBalanceLeftDetail("goldBalanceLeftDetail");

    public final SetPath<GoldBalanceChooser, QGoldBalanceChooser> leftChooser = this.<GoldBalanceChooser, QGoldBalanceChooser>createSet("leftChooser", GoldBalanceChooser.class, QGoldBalanceChooser.class, PathInits.DIRECT2);

    public final StringPath leftExample = createString("leftExample");

    public QGoldBalanceLeftDetail(String variable) {
        super(GoldBalanceLeftDetail.class, forVariable(variable));
    }

    public QGoldBalanceLeftDetail(Path<? extends GoldBalanceLeftDetail> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGoldBalanceLeftDetail(PathMetadata metadata) {
        super(GoldBalanceLeftDetail.class, metadata);
    }

}


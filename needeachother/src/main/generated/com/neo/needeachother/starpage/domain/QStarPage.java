package com.neo.needeachother.starpage.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStarPage is a Querydsl query type for StarPage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStarPage extends EntityPathBase<StarPage> {

    private static final long serialVersionUID = -1147704298L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStarPage starPage = new QStarPage("starPage");

    public final SetPath<NEOMember, QNEOMember> admins = this.<NEOMember, QNEOMember>createSet("admins", NEOMember.class, QNEOMember.class, PathInits.DIRECT2);

    public final QStarPageInfo information;

    public final ListPath<StarPageLayoutLine, QStarPageLayoutLine> layoutLines = this.<StarPageLayoutLine, QStarPageLayoutLine>createList("layoutLines", StarPageLayoutLine.class, QStarPageLayoutLine.class, PathInits.DIRECT2);

    public final QStarPageId starPageId;

    public QStarPage(String variable) {
        this(StarPage.class, forVariable(variable), INITS);
    }

    public QStarPage(Path<? extends StarPage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStarPage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStarPage(PathMetadata metadata, PathInits inits) {
        this(StarPage.class, metadata, inits);
    }

    public QStarPage(Class<? extends StarPage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.information = inits.isInitialized("information") ? new QStarPageInfo(forProperty("information"), inits.get("information")) : null;
        this.starPageId = inits.isInitialized("starPageId") ? new QStarPageId(forProperty("starPageId")) : null;
    }

}


package com.neo.needeachother.post.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVoteItem is a Querydsl query type for VoteItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVoteItem extends EntityPathBase<VoteItem> {

    private static final long serialVersionUID = -537469967L;

    public static final QVoteItem voteItem = new QVoteItem("voteItem");

    public final StringPath id = createString("id");

    public final StringPath OptionText = createString("OptionText");

    public final SetPath<Voter, QVoter> voterSet = this.<Voter, QVoter>createSet("voterSet", Voter.class, QVoter.class, PathInits.DIRECT2);

    public QVoteItem(String variable) {
        super(VoteItem.class, forVariable(variable));
    }

    public QVoteItem(Path<? extends VoteItem> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVoteItem(PathMetadata metadata) {
        super(VoteItem.class, metadata);
    }

}


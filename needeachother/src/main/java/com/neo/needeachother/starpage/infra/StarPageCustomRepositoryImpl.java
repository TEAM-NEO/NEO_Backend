package com.neo.needeachother.starpage.infra;

import com.neo.needeachother.starpage.domain.StarPageId;
import com.neo.needeachother.starpage.domain.repository.StarPageCustomRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import static com.neo.needeachother.starpage.domain.QStarPage.*;

@RequiredArgsConstructor
public class StarPageCustomRepositoryImpl implements StarPageCustomRepository {

    private final JPAQueryFactory queryFactory;

    public void findStarPageTopViewById(StarPageId id){
//        queryFactory.select(starPage.information)
//                .from(starPage)
//                .where(starPage.starPagesId.eq(id))
//                .fetchOne();

    }

    @Override
    public StarPageId getNextId(){
        return new StarPageId("SP_" + UUID.randomUUID().toString().toLowerCase());
    }

}

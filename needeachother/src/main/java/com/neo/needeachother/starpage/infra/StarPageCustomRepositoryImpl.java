package com.neo.needeachother.starpage.infra;

import com.neo.needeachother.starpage.domain.StarPageCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StarPageCustomRepositoryImpl implements StarPageCustomRepository {

    private final JPAQueryFactory queryFactory;

}

package com.neo.needeachother.post.infra;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostCustomRepositoryImpl {

    private final JPAQueryFactory jpaQueryFactory;
}

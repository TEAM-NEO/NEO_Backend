package com.neo.needeachother.member.domain.relation;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum NEOSeekerShineRelationType {
    FOLLOWER("FOL", "팔로워"),
    OWNER("OWN", "소유자");

    private final String typeCode;
    private final String description;
}

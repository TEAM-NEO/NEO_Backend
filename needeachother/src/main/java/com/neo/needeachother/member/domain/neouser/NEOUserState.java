package com.neo.needeachother.member.domain.neouser;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NEOUserState {
    ACTIVE("ACT", "활성화"),
    BLOCKED("BLO", "관리자에 의한 차단"),
    DORMANT("DOR", "휴면계정"),
    DELETED("DEL", "계정삭제");

    private final String stateCode;
    private final String description;
}

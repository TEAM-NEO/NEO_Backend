package com.neo.needeachother.member.domain.neouser;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NEOUserCreationRequestedEvent {
    private final LocalDateTime requestedAt;

    public NEOUserCreationRequestedEvent(){
        this.requestedAt = LocalDateTime.now();
    }
}

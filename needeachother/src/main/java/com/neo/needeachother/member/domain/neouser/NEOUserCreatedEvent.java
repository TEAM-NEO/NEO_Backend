package com.neo.needeachother.member.domain.neouser;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NEOUserCreatedEvent {
    private final Long createdNeoUserId;
    private final LocalDateTime createdAt;

    private NEOUserCreatedEvent(Long createdNeoUserId) {
        this.createdNeoUserId = createdNeoUserId;
        this.createdAt = LocalDateTime.now();
    }

    public static NEOUserCreatedEvent of(Long createdNeoUserId) {
        return new NEOUserCreatedEvent(createdNeoUserId);
    }
}

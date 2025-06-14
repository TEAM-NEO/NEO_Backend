package com.neo.needeachother.member.domain.neouser;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NEOUserCreatedEvent {
    private final Long createdNeoUserId;
    private final NEOUser user;
    private final LocalDateTime createdAt;

    private NEOUserCreatedEvent(NEOUser user, Long createdNeoUserId) {
        this.createdNeoUserId = createdNeoUserId;
        this.user = user;
        this.createdAt = LocalDateTime.now();
    }

    public static NEOUserCreatedEvent of(NEOUser user, Long createdNeoUserId) {
        return new NEOUserCreatedEvent(user, createdNeoUserId);
    }
}

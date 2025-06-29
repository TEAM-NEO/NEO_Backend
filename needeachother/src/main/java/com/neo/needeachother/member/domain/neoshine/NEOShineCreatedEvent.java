package com.neo.needeachother.member.domain.neoshine;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NEOShineCreatedEvent {
    private final NEOShineId createdNeoShineId;
    private final NEOShine shine;
    private final LocalDateTime createdAt;

    private NEOShineCreatedEvent(NEOShine shine, NEOShineId createdNeoShineId) {
        this.createdNeoShineId = createdNeoShineId;
        this.shine = shine;
        this.createdAt = LocalDateTime.now();
    }

    public static NEOShineCreatedEvent of(NEOShine shine, NEOShineId createdNeoShineId) {
        return new NEOShineCreatedEvent(shine, createdNeoShineId);
    }
}
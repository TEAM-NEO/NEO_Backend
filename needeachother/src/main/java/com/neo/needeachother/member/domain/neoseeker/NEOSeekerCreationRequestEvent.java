package com.neo.needeachother.member.domain.neoseeker;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NEOSeekerCreationRequestEvent {
    private final LocalDateTime requestedAt;

    private NEOSeekerCreationRequestEvent(){
        this.requestedAt = LocalDateTime.now();
    }

    public static NEOSeekerCreationRequestEvent of(){
        return new NEOSeekerCreationRequestEvent();
    }
}

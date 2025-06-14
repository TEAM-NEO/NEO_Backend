package com.neo.needeachother.member.domain.neoshine;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NEOShineId implements Serializable {

    @Column(name = "shine_id")
    private String value;

    public static NEOShineId generate() {
        return new NEOShineId("SHINE_" + UUID.randomUUID().toString().toUpperCase().replace("-", ""));
    }

    public static NEOShineId of(String value) {
        return new NEOShineId(value);
    }
}
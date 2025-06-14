package com.neo.needeachother.member.domain.neoseeker;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NEOSeekerId implements Serializable {

    @Column(name = "seeker_id")
    private String value;

    public static NEOSeekerId of(String value) {
        return new NEOSeekerId(value);
    }
}
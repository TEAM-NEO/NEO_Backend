package com.neo.needeachother.member.domain.neoseeker;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NEOSeekerIdGenerator {
    /**
     * Generates a unique NEOSeekerId.
     *
     * @return A new NEOSeekerId instance with a unique identifier.
     */
    public NEOSeekerId generate(Long userId) {
        return NEOSeekerId.of("SEEKER_" + userId +
                UUID.randomUUID().toString().toUpperCase().replace("-", ""));
    }
}

package com.neo.needeachother.member.domain.neoshine;

import com.neo.needeachother.member.domain.neoseeker.NEOSeeker;
import com.neo.needeachother.member.domain.neoseeker.NEOSeekerId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "neo_shine")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class NEOShine {

    @EmbeddedId
    private NEOShineId neoShineId;

    // 이 네오샤인 계정의 실제 소유자 (실제 개인)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_seeker_id")
    private NEOSeeker owner;

    @Embedded
    private NEOShineProfile neoShineProfile;

    @Column(name = "star_qualified_at")
    private LocalDateTime starQualifiedAt;

    @Column(name = "star_page_url", unique = true)
    private String starPageUrl;

    // 스타 자격 부여
    public static NEOShine createShine(NEOSeeker neoSeeker, NEOShineProfile profile, String starPageUrl) {
        return new NEOShine(
                NEOShineId.generate(),
                neoSeeker,
                profile,
                LocalDateTime.now(),
                starPageUrl
        );
    }
}
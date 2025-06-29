package com.neo.needeachother.member.domain.relation;

import com.neo.needeachother.member.domain.neoseeker.NEOSeeker;
import com.neo.needeachother.member.domain.neoshine.NEOShine;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "neo_seeker_shine_relation")
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NEOSeekerShineRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seeker_id")
    private NEOSeeker seeker;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shine_id")
    private NEOShine shine;

    @Column(name = "joined_at")
    private LocalDateTime joinedAt;

    @Column(name = "relation_type")
    private NEOSeekerShineRelationType relationType;

    @Column(name = "relation_status")
    private NEOSeekerShineRelationStatus relationStatus;

    public boolean isOwner() {
        return this.relationType == NEOSeekerShineRelationType.OWNER;
    }

    public boolean isFollower() {
        return  this.relationType == NEOSeekerShineRelationType.FOLLOWER;
    }


    public static NEOSeekerShineRelation createOwnerRelation(NEOSeeker seeker, NEOShine shine) {
        return NEOSeekerShineRelation.builder()
                .seeker(seeker)
                .shine(shine)
                .joinedAt(LocalDateTime.now())
                .relationType(NEOSeekerShineRelationType.OWNER)
                .relationStatus(NEOSeekerShineRelationStatus.ACTIVE)
                .build();
    }

    public static NEOSeekerShineRelation createMemberRelation(NEOSeeker seeker, NEOShine shine) {
        return NEOSeekerShineRelation.builder()
                .seeker(seeker)
                .shine(shine)
                .joinedAt(LocalDateTime.now())
                .relationType(NEOSeekerShineRelationType.FOLLOWER)
                .relationStatus(NEOSeekerShineRelationStatus.ACTIVE)
                .build();
    }
}
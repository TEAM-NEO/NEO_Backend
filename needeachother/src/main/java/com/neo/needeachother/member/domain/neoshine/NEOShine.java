package com.neo.needeachother.member.domain.neoshine;

import com.neo.needeachother.member.domain.neoseeker.NEOSeeker;
import com.neo.needeachother.member.domain.neoseeker.NEOSeekerId;
import com.neo.needeachother.member.domain.relation.NEOSeekerShineRelation;
import com.neo.needeachother.member.domain.relation.NEOSeekerShineRelationType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "neo_shine")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class NEOShine {

    @EmbeddedId
    private NEOShineId neoShineId;

    @Enumerated(EnumType.STRING)
    @Column(name = "shine_type")
    private NEOShineType shineType;

    @OneToMany(mappedBy = "shine", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<NEOSeekerShineRelation> seekerRelations = new ArrayList<>();

    @Embedded
    private NEOShineProfile neoShineProfile;

    @Column(name = "star_qualified_at")
    private LocalDateTime starQualifiedAt;

    @Column(name = "star_page_url", unique = true)
    private String starPageUrl;

    @Builder.Default
    private transient List<Object> domainEvents = new ArrayList<>();

    @Builder.Default
    private transient boolean isNewEntity = true;

    private void addDomainEvent(Object event) {
        this.domainEvents.add(event);
    }

    @DomainEvents
    Collection<Object> domainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

    @AfterDomainEventPublication
    void callbackMethod() {
        this.domainEvents.clear();
        System.out.println("NEOShine 도메인 이벤트들이 성공적으로 발행되었습니다. 이벤트 리스트를 정리합니다.");
    }

    @PostPersist
    void onPostPersist() {
        if (isNewEntity) {
            addDomainEvent(NEOShineCreatedEvent.of(this, this.neoShineId));
            isNewEntity = false;
        }
    }

    @PostLoad
    void onPostLoad() {
        this.isNewEntity = false;
    }

    /**
     * 개인 NEOShine 계정 생성 (스타 회원가입 시 기본 생성)
     */
    public static NEOShine createIndividualShine(NEOSeeker owner, NEOShineProfile profile, String starPageUrl) {
        NEOShine shine = NEOShine.builder()
                .neoShineId(NEOShineId.generate())
                .shineType(NEOShineType.INDIVIDUAL)
                .neoShineProfile(profile)
                .starQualifiedAt(LocalDateTime.now())
                .starPageUrl(starPageUrl)
                .domainEvents(new ArrayList<>())
                .build();

        // 소유자 관계 설정
        NEOSeekerShineRelation ownerRelation = NEOSeekerShineRelation.createOwnerRelation(owner, shine);
        shine.seekerRelations.add(ownerRelation);

        return shine;
    }

    /**
     * 그룹 NEOShine 계정 생성
     */
    public static NEOShine createGroupShine(NEOSeeker owner, NEOShineProfile profile, String starPageUrl) {
        NEOShine shine = NEOShine.builder()
                .neoShineId(NEOShineId.generate())
                .shineType(NEOShineType.GROUP)
                .neoShineProfile(profile)
                .starQualifiedAt(LocalDateTime.now())
                .starPageUrl(starPageUrl)
                .domainEvents(new ArrayList<>())
                .build();

        // 소유자 관계 설정
        NEOSeekerShineRelation ownerRelation = NEOSeekerShineRelation.createOwnerRelation(owner, shine);
        shine.seekerRelations.add(ownerRelation);

        return shine;
    }

    /**
     * 그룹에 새로운 멤버 추가
     */
    public void addMember(NEOSeeker seeker) {
        validateMemberAddition(seeker);
        NEOSeekerShineRelation memberRelation = NEOSeekerShineRelation.createMemberRelation(seeker, this);
        this.seekerRelations.add(memberRelation);
    }

    /**
     * 그룹에서 멤버 제거
     */
    public void removeMember(NEOSeeker seeker) {
        this.seekerRelations.removeIf(relation ->
                relation.getSeeker().equals(seeker) && !relation.isOwner());
    }

    /**
     * 그룹의 소유자들을 조회
     */
    public List<NEOSeeker> getOwners() {
        return seekerRelations.stream()
                .filter(NEOSeekerShineRelation::isOwner)
                .map(NEOSeekerShineRelation::getSeeker)
                .toList();
    }

    /**
     * 그룹의 모든 멤버들을 조회 (소유자 포함)
     */
    public List<NEOSeeker> getAllMembers() {
        return seekerRelations.stream()
                .map(NEOSeekerShineRelation::getSeeker)
                .toList();
    }

    /**
     * 그룹의 일반 멤버들만 조회 (소유자 제외)
     */
    public List<NEOSeeker> getRegularMembers() {
        return seekerRelations.stream()
                .filter(relation -> !relation.isOwner())
                .map(NEOSeekerShineRelation::getSeeker)
                .toList();
    }

    private void validateMemberAddition(NEOSeeker seeker) {
        if (this.shineType == NEOShineType.INDIVIDUAL) {
            throw new IllegalStateException("개인 계정에는 멤버를 추가할 수 없습니다.");
        }

        boolean alreadyMember = seekerRelations.stream()
                .anyMatch(relation -> relation.getSeeker().equals(seeker));

        if (alreadyMember) {
            throw new IllegalStateException("이미 그룹의 멤버입니다.");
        }
    }
}
package com.neo.needeachother.member.domain.neoseeker;

import com.neo.needeachother.member.domain.neoshine.NEOShine;
import com.neo.needeachother.member.domain.neouser.NEOUser;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "neo_seeker")
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access =  AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NEOSeeker {

    @EmbeddedId
    private NEOSeekerId neoSeekerId;

    @OneToOne(fetch = FetchType.LAZY)
    private NEOUser user;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<NEOShine> ownedShineAccounts = new ArrayList<>();

    @Builder.Default
    private transient List<Object> domainEvents = new ArrayList<>();

    @Builder.Default
    private transient boolean isNewEntity = true;

    private void addDomainEvent(Object event) {
        this.domainEvents.add(event);
    }

    // Spring Data JPA가 호출하는 메소드: 이벤트 수집
    @DomainEvents
    Collection<Object> domainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

    // Spring Data JPA가 호출하는 메소드: 이벤트 발행 후 정리
    @AfterDomainEventPublication
    void callbackMethod() {
        this.domainEvents.clear();
        System.out.println("NEOUser 도메인 이벤트들이 성공적으로 발행되었습니다. 이벤트 리스트를 정리합니다.");
    }

    @PostPersist
    void onPostPersist() {
        if (isNewEntity) {
            isNewEntity = false;
        }
    }

    @PostLoad
    void onPostLoad() {
        this.isNewEntity = false;
    }

    public static NEOSeeker of(NEOSeekerIdGenerator seekerIdGenerator, NEOUser user, Long userId) {
        return NEOSeeker.builder()
                .neoSeekerId(seekerIdGenerator.generate(userId))
                .user(user)
                .domainEvents(List.of(NEOSeekerCreationRequestEvent.of()))
                .build();
    }

    public void assignShineAccount(NEOShine shine) {
    }
}
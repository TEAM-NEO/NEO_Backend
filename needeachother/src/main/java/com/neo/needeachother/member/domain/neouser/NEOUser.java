package com.neo.needeachother.member.domain.neouser;

import com.neo.needeachother.users.enums.NEOGenderType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "neo_user")
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NEOUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * NEO를 사용하는 사용자의 기본 정보를 담고 있습니다. 계정 정보와 관련되지 않은 나머지 '회원'의 기본 정보는 모두 여기에 포함됩니다.
     */
    @Embedded
    private NEOUserBasicProfile neoUserBasicProfile;

    /**
     * NEO를 사용하는 사용자의 인증 및 인가와 관련된 정보를 담고 있습니다.
     */
    @Embedded
    private NEOUserAuthenticationProfile neoUserAuthenticationProfile;

    @Column(name = "state")
    private NEOUserState neoUserState;

    @Column(name = "joined_at")
    private LocalDateTime joinedAt;

    @Column(name = "last_active_at")
    private LocalDateTime lastActiveAt;

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
            addDomainEvent(NEOUserCreatedEvent.of(this.id));
            isNewEntity = false;
        }
    }

    @PostLoad
    void onPostLoad() {
        this.isNewEntity = false;
    }

    public static NEOUser create(String userRealName, String email, String phoneNumber, String neoNickName,
                                 String genderCode, LocalDate birthday, String neoId, String socialId, String password, String authProviderCode){

        return NEOUser.builder()
                .neoUserBasicProfile(NEOUserBasicProfile.of(userRealName, email, phoneNumber, genderCode, birthday, neoNickName))
                .neoUserAuthenticationProfile(NEOUserAuthenticationProfile.of(neoId, socialId, password, authProviderCode))
                .neoUserState(NEOUserState.ACTIVE)
                .joinedAt(LocalDateTime.now())
                .lastActiveAt(LocalDateTime.now())
                .domainEvents(List.of(new NEOUserCreationRequestedEvent()))
                .build();
    }
}

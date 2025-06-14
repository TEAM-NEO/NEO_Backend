package com.neo.needeachother.member.domain.neomember;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "neo_member")
public class NEOMember {

    @EmbeddedId
    private NEOMemberId neoMemberId;

    @Embedded
    private BasicProfile basicProfile; // 이름, 이메일, 기본 닉네임

    @Column
    private MemberStatus status; // ACTIVE, SUSPENDED, WITHDRAWN

    @Column
    private LocalDateTime joinedAt;

    // 도메인 불변식: 회원의 기본 정보는 항상 유효해야 함
    public void updateBasicProfile(String nickname, String email) {
        validateNickname(nickname);
        validateEmail(email);
        this.basicProfile = this.basicProfile.update(nickname, email);
        Events.raise(new MemberProfileUpdatedEvent(this.memberId, nickname));
    }

    // 회원 탈퇴
    public void withdraw() {
        if (this.status == MemberStatus.WITHDRAWN) {
            throw new IllegalStateException("이미 탈퇴한 회원입니다");
        }
        this.status = MemberStatus.WITHDRAWN;
        Events.raise(new MemberWithdrawnEvent(this.memberId));
    }
}
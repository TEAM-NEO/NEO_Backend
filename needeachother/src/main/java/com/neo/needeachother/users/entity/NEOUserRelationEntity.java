package com.neo.needeachother.users.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * @author 이승훈<br>
 * @since 23.08.21<br>
 * 스타와 팬의 관계를 설정하는 엔티티
 */
@Entity
@Table(name = "star_fan_map")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NEOUserRelationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private NEOUserEntity follower;

    @ManyToOne
    @JoinColumn(name = "star_id")
    private NEOStarEntity followee;

    /**
     * 팔로우 할 대상(스타)와 연관관계를 맺는 컨비니언스 메소드
     * @param followee 팔로우 대상 스타
     */
    public void setFollowee(NEOStarEntity followee){
        if(this.followee != null){
            this.followee.getFollowerList().remove(this);
        }
        this.followee = followee;
        followee.getFollowerList().add(this);
    }

    /**
     * 팔로우를 하는 사람을 관계 매칭하는 컨비니언스 메소드.
     * @param follower 팔로우 하는 유저 (스타, 팬 공통)
     */
    public void setFollower(NEOUserEntity follower){
        if(this.follower != null){
            this.follower.getSubscribedStarList().remove(this);
        }
        this.follower = follower;
        follower.getSubscribedStarList().add(this);
    }

    /**
     * 팔로우 할 대상과 팔로우 하는 사람을 한 번에 정의할 수 있는 컨비니언스 메소드
     * @param user 팔로우 하는 사람
     * @param star 팔로우 대상
     */
    public void makeRelationFanWithStar(NEOUserEntity user, NEOStarEntity star){
        setFollower(user);
        setFollowee(star);
    }
}

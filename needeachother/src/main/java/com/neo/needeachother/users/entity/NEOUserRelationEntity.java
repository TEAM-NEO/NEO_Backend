package com.neo.needeachother.users.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

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
    @JoinColumn(name = "followee_id")
    private NEOStarEntity followee;

    public void setFollowee(NEOStarEntity followee){
        if(this.followee != null){
            this.followee.getFollowerList().remove(this);
        }
        this.followee = followee;
        followee.getFollowerList().add(this);
    }
}

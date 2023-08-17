package com.neo.needeachother.users.entity;

import com.neo.needeachother.common.entity.NEOTimeDefaultEntity;
import com.neo.needeachother.users.converter.NEOGenderTypeConverter;
import com.neo.needeachother.users.enums.NEOGenderType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "user")
@SuperBuilder
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role_type", discriminatorType = DiscriminatorType.STRING)
public class NEOUserEntity extends NEOTimeDefaultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_id")
    private String userID;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String userPW;

    @Column
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "provider_type")
    private String providerType;

    @Column(name = "neo_nick_name")
    private String neoNickName;

    @Column(name = "gender")
    @Convert(converter = NEOGenderTypeConverter.class)
    private NEOGenderType gender;

    @Builder.Default
    @OneToMany(mappedBy = "follower")
    private List<NEOUserRelationEntity> subscribedStarList = new ArrayList<>();

}

package com.neo.needeachother.users.entity;

import com.neo.needeachother.common.entity.NEOTimeDefaultEntity;
import com.neo.needeachother.users.converter.NEOGenderTypeConverter;
import com.neo.needeachother.users.enums.NEOGenderType;
import com.neo.needeachother.users.enums.NEOUserType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 이승훈<br>
 * @since 23.08.21<br>
 * NEO의 모든 유저가 기본적으로 가지고 있는 정보에 대한 엔티티입니다.
 */
@Getter
@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(name = "UNIQUE_ID_EMAIL_NAME_IDX", columnNames = {"user_id", "email", "user_name"})
})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role_type", discriminatorType = DiscriminatorType.STRING)
public abstract class NEOUserEntity extends NEOTimeDefaultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userID;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String userPW;

    @Column
    private String email;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "provider_type")
    private String providerType;

    @Setter
    @Column(name = "neo_nick_name", unique = true)
    private String neoNickName;

    @Column(name = "gender")
    @Convert(converter = NEOGenderTypeConverter.class)
    private NEOGenderType gender;

    @Builder.Default
    @OneToMany(mappedBy = "follower", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<NEOUserRelationEntity> subscribedStarList = new ArrayList<>();

    public abstract NEOUserType getUserType();

}

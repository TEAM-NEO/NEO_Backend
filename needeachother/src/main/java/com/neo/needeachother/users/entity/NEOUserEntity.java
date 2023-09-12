package com.neo.needeachother.users.entity;

import com.neo.needeachother.common.entity.NEOTimeDefaultEntity;
import com.neo.needeachother.users.converter.NEOGenderTypeConverter;
import com.neo.needeachother.users.converter.NEOUserTypeConverter;
import com.neo.needeachother.users.dto.NEOAdditionalFanInfoRequest;
import com.neo.needeachother.users.dto.NEOAdditionalStarInfoRequest;
import com.neo.needeachother.users.enums.NEOGenderType;
import com.neo.needeachother.users.enums.NEOUserType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 이승훈<br>
 * @since 23.09.12<br>
 * NEO의 모든 유저가 기본적으로 가지고 있는 정보에 대한 엔티티입니다.
 */
@Getter
@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(name = "UNIQUE_ID_EMAIL_NAME_IDX", columnNames = {"neo_id", "email", "user_name"})
})
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class NEOUserEntity extends NEOTimeDefaultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_type")
    @Convert(converter = NEOUserTypeConverter.class)
    private NEOUserType userType;

    /* 추후 폼 로그인을 위해 보류, OAuth2 가입시 email 값이 userID 에도 매핑 */
    @Column(name = "neo_id")
    private String neoID;

    @Column(name = "user_name")
    private String userName;

    /* 추후 폼 로그인을 위해 보류, OAuth2 가입시 랜덤 값 생성 */
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

    @Setter
    @OneToOne
    @JoinColumn(name = "star_info_id")
    private NEOStarEntity starInformation;

    @Builder.Default
    @OneToMany(mappedBy = "follower", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, orphanRemoval = true)
    private List<NEOUserRelationEntity> subscribedStarList = new ArrayList<>();

    public void subscribeNeoStar(NEOStarEntity followWantStar){
        NEOUserRelationEntity userRelation = new NEOUserRelationEntity();
        userRelation.makeRelationFanWithStar(this, followWantStar);
    }

    public static NEOUserEntity fromFanRequest(final NEOAdditionalFanInfoRequest request){
        return NEOUserEntity.builder()
                .userType(NEOUserType.FAN)
                .neoID(request.getUserID())
                .userName(request.getUserName())
                .userPW(request.getUserPW())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .providerType(null)
                .neoNickName(request.getNeoNickName())
                .gender(request.getGender())
                .build();
    }

    public static NEOUserEntity fromStarRequest(final NEOAdditionalStarInfoRequest request){
        return NEOUserEntity.builder()
                .userType(NEOUserType.STAR)
                .neoID(request.getUserID())
                .userName(request.getUserName())
                .userPW(request.getUserPW())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .providerType(null)
                .neoNickName(request.getNeoNickName())
                .gender(request.getGender())
                .build();
    }

}

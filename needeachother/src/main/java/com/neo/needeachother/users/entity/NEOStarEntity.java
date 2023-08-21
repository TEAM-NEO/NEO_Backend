package com.neo.needeachother.users.entity;

import com.neo.needeachother.users.dto.NEOPublicStarInfoDto;
import com.neo.needeachother.users.dto.NEOStarInfoDto;
import com.neo.needeachother.users.enums.NEOStarDetailClassification;
import com.neo.needeachother.users.enums.NEOUserType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 스타의 정보를 담고 있는 스타 엔티티<br>
 * 모든 유저의 공통 정보를 포함한 {@code NEOUserEntity}를 상속 받습니다.<br>
 */
@Entity
@Getter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue(value = NEOUserType.TypeCode.STAR) // 해당 구분자로 SingleTable 전략 사용.
public class NEOStarEntity extends NEOUserEntity {

    public static final NEOUserType USER_TYPE = NEOUserType.STAR;

//    public NEOStarEntity(List<NEOStarTypeEntity> starTypeEntities){
//        this.starTypeList = starTypeEntities;
//    }

    /* 실제 스타가 사용하고 있는 스타 활동명 */
    @Column(name = "star_nickname")
    private String starNickName;

    /* 네오 스타가 가진 스타 유형 */
    @Setter
    @Builder.Default
    @OneToMany(mappedBy = "neoStar")
    private List<NEOStarTypeEntity> starTypeList = new ArrayList<>();

    /* 해당 스타 엔티티를 팔로우하고 있는 팔로워 리스트 */
    @Builder.Default
    @OneToMany(mappedBy = "followee")
    private List<NEOUserRelationEntity> followerList = new ArrayList<>();

    /**
     * {@code NEOStarInfoDto}를 통해 새로운 스타 엔티티를 생성하는 정적 팩토리 메소드입니다. <br>
     * 유효성 검사를 통과한 {@code NEOStarInfoDto} 객체를 삽입하면 사용할 수 있습니다. <br>
     * TODO : OAuth 도입 이후 요청 DTO 변경 가능성 농후.
     *
     * @param request 스타 정보 생성 요청 객체
     * @return {@code NEOStarEntity} 새로운 스타 엔티티
     */
    public static NEOStarEntity fromRequest(NEOStarInfoDto request) {
        return NEOStarEntity.builder()
                .userID(request.getUserID())
                .userName(request.getUserName())
                .userPW(request.getUserPW())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .providerType(null)
                .neoNickName(request.getNeoNickName())
                .gender(request.getGender())
                .subscribedStarList(new ArrayList<>())
                .starTypeList(new ArrayList<>())
                .followerList(new ArrayList<>())
                .build();
    }

    private HashSet<NEOStarDetailClassification> getStarClassificationSet(){
        return this.getStarTypeList().stream()
                .map(NEOStarTypeEntity::getStarType)
                .collect(Collectors.toCollection(HashSet::new));
    }

    public NEOStarInfoDto toDTO() {

        HashSet<NEOStarDetailClassification> starClassificationSet = this.getStarClassificationSet();

        return NEOStarInfoDto.builder()
                .userID(this.getUserID())
                .userName(this.getUserName())
                .email(this.getEmail())
                .neoNickName(this.getNeoNickName())
                .gender(this.getGender())
                .phoneNumber(this.getPhoneNumber())
                .starNickName(this.getStarNickName())
                .starClassificationSet(starClassificationSet)
                .build();
    }

    public NEOStarInfoDto fetchDTO(NEOStarInfoDto infoDto) {

        HashSet<NEOStarDetailClassification> starClassificationSet = this.getStarClassificationSet();

        infoDto.setUserID(this.getUserID());
        infoDto.setUserName(this.getUserName());
        infoDto.setEmail(this.getEmail());
        infoDto.setNeoNickName(this.getNeoNickName());
        infoDto.setGender(this.getGender());
        infoDto.setPhoneNumber(this.getPhoneNumber());
        infoDto.setStarNickName(this.getStarNickName());
        infoDto.setStarClassificationSet(starClassificationSet);
        return infoDto;
    }

    public NEOPublicStarInfoDto toPublicDto(){
        return NEOPublicStarInfoDto.builder()
                .starNickName(this.getStarNickName())
                .gender(this.getGender())
                .starClassificationSet(this.getStarClassificationSet())
                .build();
    }
}

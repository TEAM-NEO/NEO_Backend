package com.neo.needeachother.users.entity;

import com.neo.needeachother.users.dto.NEOFanInfoDto;
import com.neo.needeachother.users.dto.NEOPublicFanInfoDto;
import com.neo.needeachother.users.enums.NEOUserType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 네오의 팬 엔티티로, 팬 개인의 정보를 담습니다.<br>
 * 모든 유저의 공통사항이 담긴 {@NEOUserEntity}를 상속받습니다.
 */
@Entity
@Getter
@ToString
@SuperBuilder
@NoArgsConstructor
@DiscriminatorValue(value = NEOUserType.TypeCode.FAN) // SingleTable 전략 구분자
public class NEOFanEntity extends NEOUserEntity{

    public static final NEOUserType USER_TYPE = NEOUserType.FAN;

    /**
     * {@code NEOFanInfoDto}로부터 새로운 팬 엔티티를 생성하는 정적 팩토리 메소드입니다.<br>
     * 유효성 검사를 통과한 {@code NEOFanInfoDto} 객체를 삽입하면 사용할 수 있습니다. <br>
     * TODO : OAuth가 개발된 이후에는, NEOCreateFanInfoRequest가 아닌 다른 DTO로 변경 가능성 농후.
     * @param request 새로운 팬 정보 삽입 요청 객체
     * @return {@code NEOFanEntity} 새로운 팬 엔티티
     */
    public static NEOFanEntity fromRequest(final NEOFanInfoDto request){
        return NEOFanEntity.builder()
                .userID(request.getUserID())
                .userName(request.getUserName())
                .userPW(request.getUserPW())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .providerType(null)
                .neoNickName(request.getNeoNickName())
                .gender(request.getGender())
                // .subscribedStarList(new ArrayList<>())
                .build();
    }

    public NEOFanInfoDto toDTO(){
        return NEOFanInfoDto.builder()
                .userID(this.getUserID())
                .userName(this.getUserName())
                .email(this.getEmail())
                .neoNickName(this.getNeoNickName())
                .phoneNumber(this.getPhoneNumber())
                .gender(this.getGender())
                .build();
    }

    public NEOPublicFanInfoDto toPublicDTO(){
        return NEOPublicFanInfoDto.builder()
                .neoNickName(this.getNeoNickName())
                .gender(this.getGender())
                .build();
    }
}

package com.neo.needeachother.users.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neo.needeachother.users.enums.NEOUserType;
import com.neo.needeachother.users.request.NEOCreateStarInfoRequest;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@ToString
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue(value = NEOUserType.TypeCode.STAR)
public class NEOStarEntity extends NEOUserEntity {

    public static final NEOUserType USER_TYPE = NEOUserType.STAR;

    public NEOStarEntity(List<NEOStarTypeEntity> starTypeEntities){
        this.starTypeList = starTypeEntities;
    }

    @Column(name = "star_nickname")
    private String starNickName;

    @Setter
    @OneToMany(mappedBy = "neoStar")
    private List<NEOStarTypeEntity> starTypeList = new ArrayList<>();

}

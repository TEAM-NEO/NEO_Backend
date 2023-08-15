package com.neo.needeachother.users.entity;

import com.neo.needeachother.users.enums.NEOUserType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue(value = NEOUserType.TypeCode.FAN)
public class NEOFanEntity extends NEOUserEntity{

    public static final NEOUserType USER_TYPE = NEOUserType.FAN;

}

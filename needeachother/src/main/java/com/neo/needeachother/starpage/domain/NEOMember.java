package com.neo.needeachother.starpage.domain;

import com.neo.needeachother.users.entity.NEOUserEntity;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NEOMember {

    private String email;


}

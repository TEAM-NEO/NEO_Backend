package com.neo.needeachother.starpage.domain;

import com.neo.needeachother.users.entity.NEOUserEntity;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NEOMember {

    private String email;

    public static NEOMember of(String email){
        return new NEOMember(email);
    }
}

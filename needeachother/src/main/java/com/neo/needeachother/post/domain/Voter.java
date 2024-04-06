package com.neo.needeachother.post.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Voter {

    private String email;

    public static Voter of(String email){
        return new Voter(email);
    }
}

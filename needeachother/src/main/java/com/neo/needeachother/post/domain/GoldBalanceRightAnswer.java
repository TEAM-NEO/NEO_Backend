package com.neo.needeachother.post.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GoldBalanceRightAnswer {
    private String email;

    public static GoldBalanceRightAnswer of(String email){
        return new GoldBalanceRightAnswer(email);
    }
}

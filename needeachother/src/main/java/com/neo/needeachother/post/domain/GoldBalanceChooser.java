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
public class GoldBalanceChooser {
    private String email;

    public static GoldBalanceChooser of(String email){
        return new GoldBalanceChooser(email);
    }
}

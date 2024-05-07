package com.neo.needeachother.post.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LeftRightRate {
    private int leftRate;
    private int rightRate;

    public static LeftRightRate of(int leftRate, int rightRate){
        return new LeftRightRate(leftRate, rightRate);
    }
}

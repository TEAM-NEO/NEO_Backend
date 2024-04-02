package com.neo.needeachother.category.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryId implements Serializable {
    private String value;

    public static CategoryId of(String value){
        return new CategoryId(value);
    }
}

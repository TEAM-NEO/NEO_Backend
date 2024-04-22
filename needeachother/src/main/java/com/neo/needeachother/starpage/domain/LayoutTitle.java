package com.neo.needeachother.starpage.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LayoutTitle {

    @Column(name = "layout_title")
    private String value;

    public static LayoutTitle of(String value){
        return new LayoutTitle(value);
    }
}

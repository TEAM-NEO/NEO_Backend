package com.neo.needeachother.starpage.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StarPageId implements Serializable {

    @Column(name = "star_page_id")
    String value;

    public static StarPageId of(String value){
        return new StarPageId(value);
    }
}

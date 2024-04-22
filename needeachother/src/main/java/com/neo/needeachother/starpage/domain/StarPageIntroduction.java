package com.neo.needeachother.starpage.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StarPageIntroduction {

    @Lob
    @Column(name = "introduction")
    private String value;

    public String getIntroduction(){
        return this.value;
    }

    public static StarPageIntroduction of(String value){
        return new StarPageIntroduction(value);
    }
}

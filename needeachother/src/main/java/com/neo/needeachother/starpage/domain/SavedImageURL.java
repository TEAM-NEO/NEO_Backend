package com.neo.needeachother.starpage.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SavedImageURL  {

    @Column(name = "image_url")
    private String value;

    public String getValue() {
        return value;
    }

    public static SavedImageURL of(String value){
        return new SavedImageURL(value);
    }
}

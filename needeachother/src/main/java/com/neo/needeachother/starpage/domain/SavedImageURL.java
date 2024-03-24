package com.neo.needeachother.starpage.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SavedImageURL  {

    private String value;

    public String getValue() {
        return value;
    }

    public static SavedImageURL of(String value){
        return new SavedImageURL(value);
    }
}

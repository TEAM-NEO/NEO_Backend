package com.neo.needeachother.starpage.domain;

import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

    @Embedded
    private SavedImageURL url;

    public SavedImageURL getUrl() {
        return url;
    }

    public static Image of(String url) {
        return new Image(SavedImageURL.of(url));
    }

    public static Image ofDefaultProfileImage() {
        return new Image(SavedImageURL.of("Default Profile Image Link"));
    }

    public static Image ofDefaultTopRepresentativeImage() {
        return new Image(SavedImageURL.of("Default Profile Image Link"));
    }
}

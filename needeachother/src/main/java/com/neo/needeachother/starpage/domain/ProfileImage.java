package com.neo.needeachother.starpage.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("PF")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileImage extends Image{

    protected ProfileImage(String url){
        super(SavedImageURL.of(url));
    }
    public static ProfileImage ofDefaultProfileImage() {
        return new ProfileImage("Default Profile Image Link");
    }

}

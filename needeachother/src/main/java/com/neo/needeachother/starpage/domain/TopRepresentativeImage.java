package com.neo.needeachother.starpage.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@DiscriminatorValue("TR")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TopRepresentativeImage extends Image{

    protected TopRepresentativeImage(String url){
        super(SavedImageURL.of(url));
    }
    public static TopRepresentativeImage ofDefaultTopRepresentativeImage() {
        return new TopRepresentativeImage("Default Profile Image Link");
    }

}

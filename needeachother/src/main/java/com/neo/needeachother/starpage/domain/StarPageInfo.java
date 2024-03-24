package com.neo.needeachother.starpage.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StarPageInfo {

    private Image profileImage;
    private Image topRepresentativeImage;
    private StarPageHost host;
    private StarPageIntroduction introduction;

    public static StarPageInfo of(Image profileImage, Image topRepresentativeImage,
                                  StarPageHost host, StarPageIntroduction introduction){
        return new StarPageInfo(profileImage, topRepresentativeImage, host, introduction);
    }

    public static StarPageInfo withDefaultImageOf(StarPageHost host, StarPageIntroduction introduction){
        return StarPageInfo.of(Image.ofDefaultProfileImage(), Image.ofDefaultTopRepresentativeImage(), host, introduction);
    }
    public Image getCurrentProfileImage(){
        return this.profileImage;
    }

    public Image getCurrentTopRepresentativeImage(){
        return this.topRepresentativeImage;
    }

    public StarPageHost getHost(){
        return this.host;
    }

    public StarPageIntroduction getIntroduction(){
        return this.introduction;
    }

    public StarPageInfo changeTopRepresentativeImage(Image newTopRepresentativeImage){
        return new StarPageInfo(this.profileImage, newTopRepresentativeImage, this.host, this.introduction);
    }


    public StarPageInfo changeProfileImage(Image newProfileImage){
        return new StarPageInfo(newProfileImage, this.topRepresentativeImage, this.host, this.introduction);
    }

    public StarPageInfo changeHostInformation(StarPageHost starPageHost){
        return new StarPageInfo(this.profileImage, this.topRepresentativeImage, starPageHost, this.introduction);
    }

    public StarPageInfo changeStarPageIntroduction(StarPageIntroduction introduction){
        return new StarPageInfo(this.profileImage, this.topRepresentativeImage, this.host, this.introduction);
    }
}

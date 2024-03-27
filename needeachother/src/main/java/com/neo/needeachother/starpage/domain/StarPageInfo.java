package com.neo.needeachother.starpage.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StarPageInfo {

    @OneToOne
    private ProfileImage profileImage;

    @OneToOne
    private TopRepresentativeImage topRepresentativeImage;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "starNickName", column = @Column(name = "host_nickname")),
            @AttributeOverride(name = "email", column = @Column(name = "host_email"))
    })
    private StarPageHost host;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "introduction"))
    private StarPageIntroduction introduction;

    public static StarPageInfo of(ProfileImage profileImage, TopRepresentativeImage topRepresentativeImage,
                                  StarPageHost host, StarPageIntroduction introduction) {
        return new StarPageInfo(profileImage, topRepresentativeImage, host, introduction);
    }

    public static StarPageInfo withDefaultImageOf(StarPageHost host, StarPageIntroduction introduction) {
        return StarPageInfo.of(ProfileImage.ofDefaultProfileImage(), TopRepresentativeImage.ofDefaultTopRepresentativeImage(), host, introduction);
    }

    public Image getCurrentProfileImage() {
        return this.profileImage;
    }

    public Image getCurrentTopRepresentativeImage() {
        return this.topRepresentativeImage;
    }

    public StarPageHost getHost() {
        return this.host;
    }

    public StarPageIntroduction getIntroduction() {
        return this.introduction;
    }

    protected StarPageInfo changeTopRepresentativeImage(TopRepresentativeImage newTopRepresentativeImage) {
        return new StarPageInfo(this.profileImage, newTopRepresentativeImage, this.host, this.introduction);
    }


    protected StarPageInfo changeProfileImage(ProfileImage newProfileImage) {
        return new StarPageInfo(newProfileImage, this.topRepresentativeImage, this.host, this.introduction);
    }

    protected StarPageInfo changeHostInformation(StarPageHost starPageHost) {
        return new StarPageInfo(this.profileImage, this.topRepresentativeImage, starPageHost, this.introduction);
    }

    protected StarPageInfo changeStarPageIntroduction(StarPageIntroduction introduction) {
        return new StarPageInfo(this.profileImage, this.topRepresentativeImage, this.host, this.introduction);
    }
}

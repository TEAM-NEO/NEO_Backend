package com.neo.needeachother.starpage.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StarPageInfo {

    @Embedded
    @AttributeOverride(name = "url", column = @Column(name = "profile_image_url"))
    private Image profileImage;

    @Embedded
    @AttributeOverride(name = "url", column = @Column(name = "top_representative_image_url"))
    private Image topRepresentativeImage;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "starNickName", column = @Column(name = "host_nickname")),
            @AttributeOverride(name = "email", column = @Column(name = "host_email"))
    })
    private StarPageHost host;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "introduction"))
    private StarPageIntroduction introduction;

    public static StarPageInfo of(Image profileImage, Image topRepresentativeImage,
                                  StarPageHost host, StarPageIntroduction introduction) {
        return new StarPageInfo(profileImage, topRepresentativeImage, host, introduction);
    }

    public static StarPageInfo withDefaultImageOf(StarPageHost host, StarPageIntroduction introduction) {
        return StarPageInfo.of(Image.ofDefaultProfileImage(), Image.ofDefaultTopRepresentativeImage(), host, introduction);
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

    protected StarPageInfo changeTopRepresentativeImage(Image newTopRepresentativeImage) {
        return new StarPageInfo(this.profileImage, newTopRepresentativeImage, this.host, this.introduction);
    }


    protected StarPageInfo changeProfileImage(Image newProfileImage) {
        return new StarPageInfo(newProfileImage, this.topRepresentativeImage, this.host, this.introduction);
    }

    protected StarPageInfo changeHostInformation(StarPageHost starPageHost) {
        return new StarPageInfo(this.profileImage, this.topRepresentativeImage, starPageHost, this.introduction);
    }

    protected StarPageInfo changeStarPageIntroduction(StarPageIntroduction introduction) {
        return new StarPageInfo(this.profileImage, this.topRepresentativeImage, this.host, this.introduction);
    }
}

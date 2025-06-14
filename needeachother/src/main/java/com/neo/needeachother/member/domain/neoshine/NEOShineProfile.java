package com.neo.needeachother.member.domain.neoshine;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class NEOShineProfile {

    @Column(name = "stage_name")
    private String stageName; // 예명

    @Column(name = "debut_year")
    private Integer debutYear;

    @Column(name = "genre")
    private String genre;

    @Column(name = "bio", length = 1000)
    private String bio; // 자기소개

    @Column(name = "official_website")
    private String officialWebsite;
}

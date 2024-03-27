package com.neo.needeachother.starpage.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SNSLine {

    @Column(name = "sns_type")
    @Enumerated(value = EnumType.STRING)
    private SNSType type;

    @Column(name = "sns_url")
    private String url;

    public static SNSLine of(SNSType type, String url){
        return new SNSLine(type, url);
    }

}

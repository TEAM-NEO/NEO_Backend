package com.neo.needeachother.starpage.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Embeddable
@EqualsAndHashCode
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

    public static SNSLine of(String typeName, String url) {
        return new SNSLine(SNSType.valueOf(typeName), url);
    }
}

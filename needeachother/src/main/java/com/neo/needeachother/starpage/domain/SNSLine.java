package com.neo.needeachother.starpage.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SNSLine {
    private SNSType type;
    private String url;

    public static SNSLine of(SNSType type, String url){
        return new SNSLine(type, url);
    }

}

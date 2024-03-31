package com.neo.needeachother.starpage.dto;

import com.neo.needeachother.starpage.domain.SNSLine;
import com.neo.needeachother.starpage.domain.StarType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class StarPageTopView {

    private String profileImage;

    private String topRepresentativeImage;

    private String starNickName;

    private Set<StarType> starTypes;

    private List<SNSLine> snsLines;

    private String introduction;
}

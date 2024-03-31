package com.neo.needeachother.starpage.domain;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class StarPageTest {

    @Test
    public void changeProfileImageTest() {
        StarPage createdStarPage = StarPage.create("이승훈", "free_minkya@naver.com",
                Set.of("SNS_INFLUENCER", "ACTOR", "SINGER"),
                List.of(SNSLine.of(SNSType.YOUTUBE, "http://youtube.com/free_minkya")),
                "하이용");

        createdStarPage.changeProfileImage(NEOMember.of("free_minkya@naver.com"),
                new Image("changed"));

        assertEquals(createdStarPage.getInformation().getCurrentProfileImage().getUrl(), "changed");
    }

    public void changeTopRepresentativeImageTest(){

    }
}
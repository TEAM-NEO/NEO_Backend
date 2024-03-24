package com.neo.needeachother.starpage.domain;

import com.neo.needeachother.common.enums.NEODomainType;
import com.neo.needeachother.common.enums.NEOErrorCode;
import com.neo.needeachother.common.event.Events;
import com.neo.needeachother.common.exception.NEOExpectedException;
import com.neo.needeachother.common.exception.NEOUnexpectedException;
import com.neo.needeachother.starpage.domain.event.StarPageCreatedEvent;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StarPage {

    @EmbeddedId
    private StarPageId starPageId;

    // 스타 페이지 정보
    @Embedded
    private StarPageInfo information;

    // 스타 페이지 수정 가능 어드민
    private List<NEOMember> admins;

    // 스타페이지를 구성하는 레이아웃 구성요소
    private List<StarPageLayoutLine> layoutLines;


    // 도메인 : 스타페이지의 프로필 사진을 변경할 수 있다.
    public void changeProfileImage(NEOMember member, Image newProfileImage) {
        isChangeableBy(member);
        this.information = this.information.changeProfileImage(newProfileImage);
    }

    // 도메인 : 스타페이지의 대문 사진을 변경할 수 있다.
    public void changeTopRepresentativeImage(NEOMember member, Image newTopRepresentativeImage) {
        isChangeableBy(member);
        this.information = this.information.changeTopRepresentativeImage(newTopRepresentativeImage);
    }

    // 도메인 : 스타페이지에 관리자를 새로이 등록할 수 있다.
    public void registerNewAdmin(NEOMember newAdmin) {
        this.admins.add(newAdmin);
    }

    // 도메인 : 스타페이지에서 스타 활동명을 변경할 수 있다.
    public void changeStarNickName(String email, String newStarNickName) {
        information.getHost().isChangeable(email);
        this.information = Stream.of(newStarNickName)
                .map(starNickName -> this.information.getHost().changeStarNickName(starNickName))
                .map(starPageHost -> this.information.changeHostInformation(starPageHost))
                .findAny()
                .orElseThrow(() -> new NEOUnexpectedException("unexpected exceptions on changeStarNickName"));
    }

    // 도메인 : 스타페이지에서 새로운 스타 유형을 추가할 수 있다.
    public void registerNewStarType(String email, StarType newStarType) {
        information.getHost().isChangeable(email);
        this.information = Stream.of(newStarType)
                .map(starType -> this.information.getHost().appendNewStarType(starType))
                .map(starPageHost -> this.information.changeHostInformation(starPageHost))
                .findAny()
                .orElseThrow(() -> new NEOUnexpectedException("unexpected exceptions on registerNewStarType"));
    }

    // 도메인 : 스타페이지에서 새로운 SNS 정보를 추가할 수 있다.
    public void registerNewSNSLine(String email, SNSLine newSNSLine) {
        information.getHost().isChangeable(email);
        this.information = Stream.of(newSNSLine)
                .map(snsLine -> this.information.getHost().appendNewSNSLine(snsLine))
                .map(starPageHost -> this.information.changeHostInformation(starPageHost))
                .findAny()
                .orElseThrow(() -> new NEOUnexpectedException("unexpected exceptions on registerNewSNSLine"));

    }

    // 도메인: 스타페이지에서 기존 스타 유형을 삭제할 수 있다.
    public void removeStarType(String email, StarType existedStarType) {
        information.getHost().isChangeable(email);
        this.information = Stream.of(existedStarType)
                .map(starType -> this.information.getHost().deleteStarType(starType))
                .map(starPageHost -> this.information.changeHostInformation(starPageHost))
                .findAny()
                .orElseThrow(() -> new NEOUnexpectedException("unexpected exceptions on removeStarType"));
    }

    // 도메인 : 스타페이지에서 기존 SNS 정보 라인 하나를 삭제할 수 있다.
    public void removeSNSLine(String email, SNSLine existedSNSLine) {
        information.getHost().isChangeable(email);
        this.information = Stream.of(existedSNSLine)
                .map(snsLine -> this.information.getHost().deleteSNSLine(snsLine))
                .map(starPageHost -> this.information.changeHostInformation(starPageHost))
                .findAny()
                .orElseThrow(() -> new NEOUnexpectedException("unexpected exceptions on removeSNSLine"));
    }

    // 도메인 : 스타페이지에서 스타페이지 소개 글을 작성할 수 잇다.
    public void changeStarPageIntroduction(NEOMember member, StarPageIntroduction newIntroduction) {
        isChangeableBy(member);
        this.information = Stream.of(newIntroduction)
                .map(introduction -> this.information.changeStarPageIntroduction(introduction))
                .findAny()
                .orElseThrow(() -> new NEOUnexpectedException("unexpected exceptions on changeStarPageIntroduction"));
    }

    // 도메인 : 스타페이지 관리자만 스타페이지를 변경할 수 있다.
    private void isChangeableBy(NEOMember member) {
        if (!this.admins.contains(member)) {
            throw new NEOExpectedException(NEODomainType.STARPAGE,
                    NEOErrorCode.NOT_ADMIN_THIS_STARPAGE,
                    NEOErrorCode.NOT_ADMIN_THIS_STARPAGE.getErrorDescription());
        }
    }

    // 도메인 : 레이아웃의 순서를 조정할 수 있다.

    // 도메인 : 레이아웃의 특정 레이아웃 라인을 제거할 수 있다.

    // 도메인 : 레이아웃의 카테고리컬 레이아웃 라인을 추가할 수 있다.
    public void addCategoricalLayoutLine(NEOMember member, CategoricalLayoutLine layoutLine) {
        isChangeableBy(member);

    }

    private List<StarPageLayoutLine> getLayoutLinesAddOne(
            List<StarPageLayoutLine> currentLayoutLine, CategoricalLayoutLine categoricalLayoutLine) {
        // TODO : fill up this code
        return currentLayoutLine;
    }


    // 도메인 : 스타페이지를 새롭게 생성할 수 있다.
    public static StarPage create(String starNickName, String email, Set<StarType> starTypeSet,
                                  List<SNSLine> snsLines, String starPageIntroduce) {

        StarPage createdStarPage = new StarPage(new StarPageId(),
                StarPageInfo.withDefaultImageOf(
                        StarPageHost.of(starNickName, email, starTypeSet, snsLines),
                        StarPageIntroduction.of(starPageIntroduce)), List.of(new NEOMember(email)),
                List.of(StarPageUniqueLayoutLine.representativeArticleLayoutLine(),
                        StarPageUniqueLayoutLine.scheduleLayoutLine()));

        // 스타페이지 생성 이벤트 발행
        Events.raise(new StarPageCreatedEvent());
        return createdStarPage;
    }

}

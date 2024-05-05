package com.neo.needeachother.starpage.domain;

import com.neo.needeachother.category.domain.*;
import com.neo.needeachother.common.enums.NEODomainType;
import com.neo.needeachother.common.enums.NEOErrorCode;
import com.neo.needeachother.common.exception.NEOExpectedException;
import com.neo.needeachother.common.exception.NEOUnexpectedException;
import com.neo.needeachother.starpage.domain.domainservice.CategoryVerifyForLayoutService;
import com.neo.needeachother.starpage.domain.domainservice.CreateCategoryFromStarPageService;
import com.neo.needeachother.starpage.domain.dto.LayoutHeadLine;
import com.neo.needeachother.starpage.domain.dto.VerifiedCategoricalLayoutInformation;
import com.neo.needeachother.starpage.domain.repository.StarPageRepository;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Getter
@Table(name = "neo_starpage")
public class StarPage {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "star_page_id"))
    private StarPageId starPageId;

    // 스타 페이지 정보
    @Embedded
    private StarPageInfo information;

    // 스타 페이지 수정 가능 어드민
    @ElementCollection
    @CollectionTable(name = "neo_starpage_admin", joinColumns = @JoinColumn(name = "star_page_id"))
    private Set<NEOMember> admins = new HashSet<>();

    // 스타페이지를 구성하는 레이아웃 구성요소
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @JoinColumn(name = "star_page_id")
    @OrderColumn(name = "layout_order")
    private List<StarPageLayoutLine> layoutLines = new ArrayList<>();

    public StarPage(StarPageId starPageId, StarPageInfo information, Set<NEOMember> admins, List<StarPageLayoutLine> layoutLines) {
        this.starPageId = starPageId;
        this.information = information;
        this.admins = admins;
        this.layoutLines = new ArrayList<>(layoutLines);
    }

    protected StarPage() {
    }


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
    public void registerNewAdmin(String hostEmail, NEOMember newAdmin) {
        information.getHost().isChangeable(hostEmail);
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
    public void isChangeableBy(NEOMember member) {
        if (!this.admins.contains(member)) {
            throw new NEOExpectedException(NEODomainType.STARPAGE,
                    NEOErrorCode.NOT_ADMIN_THIS_STARPAGE,
                    NEOErrorCode.NOT_ADMIN_THIS_STARPAGE.getErrorDescription());
        }
    }

    // 도메인 : 레이아웃의 순서를 조정할 수 있다.
    public void changeOrderLayoutLine(NEOMember member, Map<Long, Integer> layoutLineIdToOrderMap) {
        isChangeableBy(member);
        if (!this.canChangeLayoutOrder(this.layoutLines, layoutLineIdToOrderMap)) {
            throw new NEOExpectedException(NEODomainType.STARPAGE,
                    NEOErrorCode.WRONG_LAYOUT_ELEMENTS,
                    NEOErrorCode.WRONG_LAYOUT_ELEMENTS.getErrorDescription());
        }
        this.layoutLines = this.getLayoutLinesChangeOrder(this.layoutLines, layoutLineIdToOrderMap);
    }

    private boolean canChangeLayoutOrder(List<StarPageLayoutLine> currentLayoutLines,
                                         Map<Long, Integer> layoutLineIdToOrderMap) {
        return currentLayoutLines.stream()
                .map(StarPageLayoutLine::getLayoutId)
                .collect(Collectors.toSet())
                .containsAll(layoutLineIdToOrderMap.keySet())
                && currentLayoutLines.size() == layoutLineIdToOrderMap.size();
    }

    private List<StarPageLayoutLine> getLayoutLinesChangeOrder(List<StarPageLayoutLine> currentLayoutLines,
                                                               Map<Long, Integer> layoutLineIdToOrderMap) {

        StarPageLayoutLine[] modifiedStarPageLayoutLineArray = new StarPageLayoutLine[currentLayoutLines.size()];
        currentLayoutLines.forEach(starPageLayoutLine -> {
            Integer order = layoutLineIdToOrderMap.get(starPageLayoutLine.getLayoutId());
            if (order >= modifiedStarPageLayoutLineArray.length || modifiedStarPageLayoutLineArray[order] != null) {
                throw new NEOExpectedException(NEODomainType.STARPAGE,
                        NEOErrorCode.WRONG_LAYOUT_ELEMENTS,
                        NEOErrorCode.WRONG_LAYOUT_ELEMENTS.getErrorDescription());
            }
            modifiedStarPageLayoutLineArray[order] = starPageLayoutLine;
        });

        return Collections.unmodifiableList(Arrays.stream(modifiedStarPageLayoutLineArray).toList());
    }

    // 도메인 : 레이아웃의 특정 레이아웃 라인을 제거할 수 있다.
    public void removeLayoutLine(NEOMember member, CategoricalLayoutLine layoutLineToRemove) {
        isChangeableBy(member);
        this.layoutLines = this.getLayoutLinesRemoveOne(this.layoutLines, layoutLineToRemove);
    }

    private List<StarPageLayoutLine> getLayoutLinesRemoveOne(
            List<StarPageLayoutLine> currentLayoutLines, CategoricalLayoutLine categoricalLayoutLine) {
        List<StarPageLayoutLine> modifiedLayoutLines = new ArrayList<>(currentLayoutLines);
        if (modifiedLayoutLines.stream()
                .anyMatch(categoricalLayoutLine::equals)) {
            modifiedLayoutLines.remove(categoricalLayoutLine);
        }
        return Collections.unmodifiableList(modifiedLayoutLines);
    }

    // 도메인 : 레이아웃의 카테고리컬 레이아웃 라인을 끝에 추가할 수 있다.
    public void appendCategoricalLayoutLine(NEOMember member, CategoryVerifyForLayoutService categoryVerifyForLayoutService,
                                            CategoryId categoryId, boolean isHorizontalLayout) {
        isChangeableBy(member);
        VerifiedCategoricalLayoutInformation categoricalLayoutInfo =
                categoryVerifyForLayoutService.verifyCategoryAndGetInformationForLayout(this.starPageId, categoryId, isHorizontalLayout);

        this.layoutLines = this.getLayoutLinesAppendOne(this.layoutLines,
                new CategoricalLayoutLine(LayoutTitle.of(categoricalLayoutInfo.getCategoryTitle()),
                        categoricalLayoutInfo.getCategoryId(),
                        categoricalLayoutInfo.getStarPageLayoutType()));
    }

    private List<StarPageLayoutLine> getLayoutLinesAppendOne(
            List<StarPageLayoutLine> currentLayoutLine, CategoricalLayoutLine categoricalLayoutLine) {
        if (currentLayoutLine.stream()
                .anyMatch(categoricalLayoutLine::equals)) {
            return currentLayoutLine;
        }

        List<StarPageLayoutLine> modifiedLayoutLines = new ArrayList<>(currentLayoutLine);
        modifiedLayoutLines.add(categoricalLayoutLine);
        return Collections.unmodifiableList(modifiedLayoutLines);
    }

    // 도메인 : 스타페이지는 고유 레이아웃을 추가할 수 있다.
    private void addStarPageUniqueLayoutLines(List<StarPageUniqueLayoutLine> uniqueLayoutLines) {
        this.layoutLines = this.getStarPageUniqueLayoutLinesAddList(this.layoutLines, uniqueLayoutLines);
    }

    private List<StarPageLayoutLine> getStarPageUniqueLayoutLinesAddList(
            List<StarPageLayoutLine> currentLayoutLine, List<StarPageUniqueLayoutLine> uniqueLayoutLines) {
        List<StarPageLayoutLine> modifiedLayoutLines = new ArrayList<>(currentLayoutLine);
        uniqueLayoutLines.stream()
                .filter(layoutLine -> !currentLayoutLine.contains(layoutLine))
                .forEach(modifiedLayoutLines::add);
        return Collections.unmodifiableList(modifiedLayoutLines);
    }

    // 도메인 : 레이아웃의 구성 순서대로 메인 스타페이지에 노출될 게시물 헤드라인 관련정보를 얻을 수 있다.
    public List<LayoutHeadLine> getLayoutHeadLines(StarPageRepository starPageRepository){
        return this.layoutLines.stream()
                .map(layoutLine -> new LayoutHeadLine(layoutLine.getLayoutTitle().getValue(),
                        layoutLine.getHeadLineByLayout(this.starPageId, starPageRepository)))
                .toList();
    }


    // 도메인 : 스타페이지로 하여금 통합 카테고리를 생성할 수 있다. (팩토리)
    public Category createCommonTypeCategory(CreateCategoryFromStarPageService createCategoryService,
                                             String title, ContentRestriction contentRestriction) {
        return createCategoryWithContentType(createCategoryService, title,
                contentRestriction, ContentType.COMMON);
    }

    // 도메인 : 스타페이지로 하여금 앨범 카테고리를 생성할 수 있다. (팩토리)
    public Category createAlbumTypeCategory(CreateCategoryFromStarPageService createCategoryService,
                                            String title, ContentRestriction contentRestriction) {
        return createCategoryWithContentType(createCategoryService, title,
                contentRestriction, ContentType.ALBUM);
    }

    // 도메인 : 스타페이지로 하여금 OX 카테고리를 생성할 수 있다. (팩토리)
    public Category createGoldBalanceTypeCategory(CreateCategoryFromStarPageService createCategoryService,
                                                  String title, ContentRestriction contentRestriction) {
        return createCategoryWithContentType(createCategoryService, title,
                contentRestriction, ContentType.GOLD_BALANCE);
    }

    // 도메인 : 스타페이지로 하여금 투표 카테고리를 생성할 수 있다. (팩토리)
    public Category createVoteTypeCategory(CreateCategoryFromStarPageService createCategoryService,
                                           String title, ContentRestriction contentRestriction) {
        return createCategoryWithContentType(createCategoryService, title,
                contentRestriction, ContentType.VOTE);
    }

    private Category createCategoryWithContentType(CreateCategoryFromStarPageService createCategoryService,
                                                   String title, ContentRestriction contentRestriction,
                                                   ContentType contentType) {
        return new Category(createCategoryService.createCategoryId(contentType),
                this.starPageId, CategoryStatus.OPEN, contentType,
                CategoryInformation.of(title), contentRestriction);
    }

    // 도메인 : 스타페이지를 새롭게 생성할 수 있다. (정적 팩토리)
    public static StarPage create(StarPageId starPageId, String starNickName, String email, Set<String> starTypeSet,
                                  List<SNSLine> snsLines, String starPageIntroduce) {

        StarPage createdStarPage = new StarPage(starPageId,
                StarPageInfo.withDefaultImageOf(
                        StarPageHost.of(starNickName, email,
                                starTypeSet.stream()
                                        .map(StarType::valueOf)
                                        .collect(Collectors.toSet()), snsLines),
                        StarPageIntroduction.of(starPageIntroduce)), Set.of(new NEOMember(email)),
                List.of(StarPageUniqueLayoutLine.representativeArticleLayoutLine(),
                        StarPageUniqueLayoutLine.scheduleLayoutLine()));
        return createdStarPage;
    }

}

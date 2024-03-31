# 💜 NEO(Need Each Other)

<p align="center"><img src=".gitbook/assets/pinterest_profile_image.png" height="600px" width="600px"></p>

> `NEO`는 Need Each Other로, 스타와 팬은 서로를 필요로 하는 관계에서 착안해 제작중인 서비스입니다.
> 스타의 일상을 공유하고, 자유와 제한을 통해 성숙한 소통을 할 수 있는 `스타페이지`, 팬들에게 질문해 가장 많이 공감하는 말을 들을 수 있는 `스타톡`,
> 암표걱정 없는 `콘서트/팬미팅 예매 기능`을 목표로 제작중입니다.


<p align="center"><img src="neo_ui_example.png" height="900px" width="900px"></p>

<br></br>

---

<br></br>



## 💜 StarPage Domain

> 예시 UI, 애그리거트, 도메인 모델 클래스 다이어그램, 요구사항 정리 <br>
https://codingleeseunghoon.notion.site/NEO-DDD-3de77b437e2e46ed9370fda0dadfe87b?pvs=4<br>        


#### 핵심 애그리거트 루트
```
@Entity
@Getter
@Table(name = "neo_starpage")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StarPage {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "star_page_id"))
    private StarPageId starPagesId;

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
    private void isChangeableBy(NEOMember member) {
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
    public void appendCategoricalLayoutLine(NEOMember member, CategoricalLayoutLine layoutLineToAppend) {
        isChangeableBy(member);
        this.layoutLines = this.getLayoutLinesAppendOne(this.layoutLines, layoutLineToAppend);
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


    // 도메인 : 스타페이지를 새롭게 생성할 수 있다.
    public static StarPage create(String starNickName, String email, Set<String> starTypeSet,
                                  List<SNSLine> snsLines, String starPageIntroduce) {

        StarPage createdStarPage = new StarPage(new StarPageId(),
                StarPageInfo.withDefaultImageOf(
                        StarPageHost.of(starNickName, email,
                                starTypeSet.stream()
                                        .map(StarType::valueOf)
                                        .collect(Collectors.toSet()), snsLines),
                        StarPageIntroduction.of(starPageIntroduce)), Set.of(new NEOMember(email)),
                List.of(StarPageUniqueLayoutLine.representativeArticleLayoutLine(),
                        StarPageUniqueLayoutLine.scheduleLayoutLine()));

        // 스타페이지 생성 이벤트 발행
        Events.raise(new StarPageCreatedEvent());
        return createdStarPage;
    }

}

```

<br></br>

---

<br></br>


## 💜 NEO(Need Each Other) 사용 기술 스택
- Language : `Java 17`
- Framework : `SpringBoot 3.1.2`
- ORM : `JPA`, `Hibernates`, `Spring Data JPA`
- Dynamic Query : `QueryDSL-JPA`
- For Cache : `Redis`
- Env Setting : `Docker`
- API Docs : `Spring REST Docs`

<br></br>
<br></br>

## 💜 NEO(Need Each Other) 시스템 아키텍처
- 

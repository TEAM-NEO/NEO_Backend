package com.neo.needeachother.category.domain;

import com.neo.needeachother.category.domain.domainservice.ConfirmCategoryChangeableAdminService;
import com.neo.needeachother.category.infra.CategoryStatusConverter;
import com.neo.needeachother.category.infra.ContentTypeConverter;
import com.neo.needeachother.common.exception.NEOUnexpectedException;
import com.neo.needeachother.post.domain.*;
import com.neo.needeachother.starpage.domain.StarPageId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Entity
@Table(name = "neo_starpage_category")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @EmbeddedId
    private CategoryId categoryId;

    @Getter
    @Column(name = "starpage_id")
    private StarPageId starPageId;

    @Column(name = "category_status")
    @Convert(converter = CategoryStatusConverter.class)
    private CategoryStatus categoryStatus;

    @Column(name = "content_type")
    @Convert(converter = ContentTypeConverter.class)
    private ContentType contentType;

    @Embedded
    @AttributeOverride(name = "categoryTitle", column = @Column(name = "title"))
    private CategoryInformation categoryInformation;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "onlyHostWriteContent", column = @Column(name = "only_host_write_content")),
            @AttributeOverride(name = "isWriteAbleComment", column = @Column(name = "comment_writeable")),
            @AttributeOverride(name = "useCommentRatingFilter", column = @Column(name = "comment_rating_filter")),
            @AttributeOverride(name = "filteringRate", column = @Column(name = "filter_rate")),
    })
    private ContentRestriction restriction;

    // 도메인 : 이 카테고리가 속한 스타페이지의 관리자에 해당하는 자만 카테고리를 변경할 수 있다.
    private void isChangeAble(ConfirmCategoryChangeableAdminService confirmService, String email) {
        confirmService.isChangeableCategoryBy(email, this.starPageId);
    }

    // 도메인 : 이 카테고리가 속한 스타페이지의 관리자에 해당하는 자만 카테고리를 삭제할 수 있으며
    // 스타페이지 메인화면에 노출중이라면 삭제할 수 없다.
    private void isRemoveAble(ConfirmCategoryChangeableAdminService confirmService, String email) {
        isChangeAble(confirmService, email);
        if (this.categoryStatus == CategoryStatus.EXPOSURE) {
            throw new NEOUnexpectedException("스타페이지 메인화면에 노출중인 카테고리는 삭제할 수 없음.");
        }
    }

    // 도메인 : 삭제한 카테고리를 다시 되살릴 수 있다.
    public void reOpenCategory(ConfirmCategoryChangeableAdminService confirmService, String email) {
        // 요청자가 카테고리를 변경할 수 있는 권한이 있는지 확인 후 상태 변경
        isChangeAble(confirmService, email);
        this.categoryStatus = CategoryStatus.OPEN;
        // TODO : 카테고리 상태 변경 -> 이벤트 발생 -> 해당 카테고리 내의 포스트 Deleted 상태를 모두 OPEN으로 일괄 변경.
    }

    // 도메인 : 생성한 카테고리를 삭제할 수 있다.
    public void deleteCategory(ConfirmCategoryChangeableAdminService confirmService, String email) {
        // 요청자가 카테고리를 변경할 수 있는 권한이 있는지 확인 후 상태 변경
        isRemoveAble(confirmService, email);
        this.categoryStatus = CategoryStatus.DELETED;
        // TODO : 카테고리 상태 변경 -> 이벤트 발생 -> 해당 카테고리 내의 포스트를 모두 Deleted 상태로 일괄 변경
    }

    // 도메인 : 카테고리를 스타페이지 메인 화면에 노출시킬 수 있다.
    public void exposureCategory(ConfirmCategoryChangeableAdminService confirmService, String email) {
        isRemoveAble(confirmService, email);
        this.categoryStatus = CategoryStatus.EXPOSURE;
    }

    // 도메인 : 기존의 카테고리 이름을 변경할 수 있다.
    public void modifyCategoryTitle(ConfirmCategoryChangeableAdminService confirmService,
                                    String email, String modifyingTitle) {
        isChangeAble(confirmService, email);
        this.categoryInformation = Stream.of(modifyingTitle)
                .map(this.categoryInformation::changeCategoryTitle)
                .filter(categoryInfo -> !categoryInfo.equals(this.categoryInformation))
                .findAny()
                .orElseThrow(() -> new NEOUnexpectedException("변경 후 카테고리의 이름이 이전과 동일합니다."));
    }

    // 도메인 : 상위 N%의 댓글만 호스트에게 보여주는 지지투표 비율을 조정할 수 있다. (팬은 모두 보임.)
    public void modifyFilteringRate(ConfirmCategoryChangeableAdminService confirmService,
                                    String email, int filteringRate) {
        isChangeAble(confirmService, email);
        this.restriction = Stream.of(filteringRate)
                .map(this.restriction::changeFilteringRate)
                .findAny()
                .orElseThrow();
    }

    // 도메인 : 댓글 지지투표 기능을 켤 수 있다.
    public void turnOnCommentRatingFilter(ConfirmCategoryChangeableAdminService confirmService,
                                          String email) {
        isChangeAble(confirmService, email);
        this.restriction = this.restriction.turnOnCommentRatingFilter();
    }

    // 도메인 : 댓글 지지투표 기능을 끌 수 있다.
    public void turnOffCommentRatingFilter(ConfirmCategoryChangeableAdminService confirmService,
                                           String email) {
        isChangeAble(confirmService, email);
        this.restriction = this.restriction.turnOffCommentRatingFilter();
    }

    // 도메인 : 스타페이지가 생성될 때 초기 카테고리 4가지를 자동 생성하게 된다.
    public static List<Category> getInitialCategoryByStarPageId(List<CategoryId> categoryIds, StarPageId id) {
        checkInitialCategoryId(categoryIds);
        return List.of(createNoticeCategory(categoryIds.get(InitCategory.NOTICE.ordinal()), id),
                createFreeBoardCategory(categoryIds.get(InitCategory.FREE_BOARD.ordinal()), id),
                createSelfiCategory(categoryIds.get(InitCategory.SELFI.ordinal()), id),
                createFreeAlbumCategory(categoryIds.get(InitCategory.FREE_ALBUM.ordinal()), id));
    }

    private static void checkInitialCategoryId(List<CategoryId> categoryIds) {
        Arrays.stream(InitCategory.values())
                .map(initCategory -> List.of(categoryIds.get(initCategory.ordinal()).getValue().split("_")[0],
                        initCategory.getContentType().getPrefixCategoryId()))
                .filter(idAndPrefixList -> !idAndPrefixList.get(0).equals(idAndPrefixList.get(1)))
                .findAny()
                .ifPresent(l -> {
                    throw new NEOUnexpectedException("");
                });
    }

    private static Category createNoticeCategory(CategoryId categoryId, StarPageId starPageId) {
        return new Category(categoryId, starPageId, CategoryStatus.OPEN,
                InitCategory.NOTICE.getContentType(),
                CategoryInformation.of(InitCategory.NOTICE.getKoreanTitle()),
                ContentRestriction.onlyHostWriteContentAndAllCanWriteComment());
    }

    private static Category createFreeBoardCategory(CategoryId categoryId, StarPageId starPageId) {
        return new Category(categoryId, starPageId, CategoryStatus.OPEN,
                InitCategory.FREE_BOARD.getContentType(),
                CategoryInformation.of(InitCategory.FREE_BOARD.getKoreanTitle()),
                ContentRestriction.onlyHostWriteContentAndAllCanWriteComment());
    }

    private static Category createSelfiCategory(CategoryId categoryId, StarPageId starPageId) {
        return new Category(categoryId, starPageId, CategoryStatus.OPEN,
                InitCategory.SELFI.getContentType(),
                CategoryInformation.of(InitCategory.SELFI.getKoreanTitle()),
                ContentRestriction.onlyHostWriteContentAndAllCanWriteComment());
    }

    private static Category createFreeAlbumCategory(CategoryId categoryId, StarPageId starPageId) {
        return new Category(categoryId, starPageId, CategoryStatus.OPEN,
                InitCategory.FREE_ALBUM.getContentType(),
                CategoryInformation.of(InitCategory.FREE_ALBUM.getKoreanTitle()),
                ContentRestriction.onlyHostWriteContentAndAllCanWriteComment());
    }

    public CommonPost writeCommonPost(String title, Author author) {
        checkCategoryContentType(ContentType.COMMON);
        return new CommonPost(this.categoryId, title, author, PostStatus.OPEN);
    }

    public AlbumPost writeAlbumPost(String title, Author author, AlbumImage image) {
        checkCategoryContentType(ContentType.ALBUM);
        return new AlbumPost(this.categoryId, title, author, PostStatus.OPEN, image);
    }

    public GoldBalancePost writeGoldBalancePost(String title, Author author,
                                                String question, String leftExample, String rightExample) {
        checkCategoryContentType(ContentType.GOLD_BALANCE);
        return new GoldBalancePost(this.categoryId, title, author, PostStatus.OPEN,
                question, leftExample, rightExample);
    }

    public VotePost writeVotePost(String title, Author author,
                                  String question, int timeToLive, List<VoteItem> voteItems) {
        checkCategoryContentType(ContentType.VOTE);
        return new VotePost(this.categoryId, title, author, PostStatus.OPEN,
                question, VoteStatus.OPEN, timeToLive, voteItems);
    }

    private void checkCategoryContentType(ContentType contentType) {
        if (this.contentType != contentType) {
            throw new NEOUnexpectedException("카테고리의 컨텐츠 타입과 작성하려는 포스트의 타입이 일치하지 않습니다.");
        }
    }
}

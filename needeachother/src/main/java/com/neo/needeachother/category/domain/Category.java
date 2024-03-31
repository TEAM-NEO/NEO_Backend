package com.neo.needeachother.category.domain;

import com.neo.needeachother.category.infra.CategoryStatusConverter;
import com.neo.needeachother.category.infra.ContentTypeConverter;
import com.neo.needeachother.starpage.domain.StarPageId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "neo_starpage_category")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @EmbeddedId
    private CategoryId categoryId;

    @Column(name = "starpage_id")
    private StarPageId starPageId;

    @Column(name = "category_status")
    @Convert(converter = CategoryStatusConverter.class)
    private CategoryStatus categoryStatus;

    @Column(name = "content_type")
    @Convert(converter = ContentTypeConverter.class)
    private ContentType contentType;

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
        isChangeAble(confirmService, email);
        this.categoryStatus = CategoryStatus.DELETED;
        // TODO : 카테고리 상태 변경 -> 이벤트 발생 -> 해당 카테고리 내의 포스트를 모두 Deleted 상태로 일괄 변경
    }

}

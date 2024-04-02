package com.neo.needeachother.category.domain;

import com.neo.needeachother.common.exception.NEOUnexpectedException;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentRestriction {

    // 제약 사항 : 컨텐츠를 호스트만 작성 가능 ? 팬도 작성가능
    private boolean onlyHostWriteContent;

    // 제약 사항 : 댓글 제한 ? 댓글 가능
    private boolean isWriteAbleComment;

    // 제약 사항 : 댓글 필터링 여부 (팬에게는 모두 보이고, 호스트에게는 일정 수 이상의 댓글만 보여짐.)
    private boolean useCommentRatingFilter;

    // 제약 사항 : 필터링 비율(0~100, 정수)
    private int filteringRate;

    // 도메인 : 필터링 비율은 0~100 정수 범위다.
    private boolean isOutOfFilteringRateBound(int filteringRate) {
        return 0 > filteringRate || filteringRate > 100;
    }

    // 도메인 : 필터링 비율을 변경할 수 있다.
    protected ContentRestriction changeFilteringRate(int filteringRate) {
        if (isOutOfFilteringRateBound(filteringRate)) {
            throw new NEOUnexpectedException("정상적인 필터링 비율 정수 범위가 아님");
        }
        return ContentRestriction.of(this.onlyHostWriteContent,
                this.isWriteAbleComment,
                this.useCommentRatingFilter,
                filteringRate);
    }

    // 도메인 : 댓글 지지투표 필터링 기능을 켤 수 있다.
    protected ContentRestriction turnOnCommentRatingFilter(){
        return ContentRestriction.of(this.onlyHostWriteContent,
                this.isWriteAbleComment,
                true,
                this.filteringRate);
    }

    // 도메인 : 댓글 지지투표 필터링 기능을 끌 수 있다.
    protected ContentRestriction turnOffCommentRatingFilter(){
        return ContentRestriction.of(this.onlyHostWriteContent,
                this.isWriteAbleComment,
                false,
                this.filteringRate);
    }

    // 제한 사항 VO 정적 팩토리 메소드
    public static ContentRestriction of(boolean onlyHostWriteContent, boolean isWriteAbleComment,
                                        boolean useCommentRatingFilter, int filteringRate) {
        return new ContentRestriction(onlyHostWriteContent, isWriteAbleComment,
                useCommentRatingFilter, filteringRate);
    }

    public static ContentRestriction onlyHostWriteContentAndAllCanWriteComment(){
        return ContentRestriction.of(true, true, false, 0);
    }
}

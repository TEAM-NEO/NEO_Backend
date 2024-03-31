package com.neo.needeachother.category.domain;

import com.neo.needeachother.common.exception.NEOUnexpectedException;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
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

    private boolean isOutOfFilteringRateBound(int filteringRate){
        return 0 > filteringRate || filteringRate > 100;
    }

    public void changeFilteringRate(int filteringRate){
        if(isOutOfFilteringRateBound(filteringRate)){
            throw new NEOUnexpectedException("정상적인 필터링 비율 정수 범위가 아님");
        }
        this.filteringRate = filteringRate;
    }

}

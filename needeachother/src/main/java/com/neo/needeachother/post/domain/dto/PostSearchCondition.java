package com.neo.needeachother.post.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostSearchCondition {
    private boolean orderByCreatedAt;
    private boolean orderByLikeCount;
    private boolean onlySearchHostWritten;
    private boolean onlySearchHostHearted;

    public static PostSearchCondition of(boolean orderByCreatedAt, boolean orderByLikeCount,
                                         boolean onlySearchHostWritten, boolean onlySearchHostHearted){
        return PostSearchCondition.builder()
                .orderByCreatedAt(orderByCreatedAt)
                .orderByLikeCount(orderByLikeCount)
                .onlySearchHostWritten(onlySearchHostWritten)
                .onlySearchHostHearted(onlySearchHostHearted)
                .build();
    }

}

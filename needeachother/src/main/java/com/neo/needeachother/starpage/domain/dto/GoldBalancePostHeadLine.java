package com.neo.needeachother.starpage.domain.dto;

import com.neo.needeachother.category.domain.ContentType;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class GoldBalancePostHeadLine extends StarPageHeadLine {
    private final String question;
    private final String leftExample;
    private final String rightExample;
    private final int leftCount;
    private final int rightCount;
    private final int leftRate;
    private final int rightRate;

    public GoldBalancePostHeadLine(Long postId, int likeCount, String question, String leftExample,
                                   String rightExample, int leftCount, int rightCount,
                                   int leftRate, int rightRate){
        super(postId, likeCount, ContentType.GOLD_BALANCE.name());
        this.question = question;
        this.leftExample = leftExample;
        this.rightExample = rightExample;
        this.leftCount = leftCount;
        this.rightCount = rightCount;
        this.leftRate = leftRate;
        this.rightRate = rightRate;
    }
}

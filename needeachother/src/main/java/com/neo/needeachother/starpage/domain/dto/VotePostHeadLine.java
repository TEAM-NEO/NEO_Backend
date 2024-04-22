package com.neo.needeachother.starpage.domain.dto;

import com.neo.needeachother.category.domain.ContentType;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString(callSuper = true)
public class VotePostHeadLine extends StarPageHeadLine {
    private final String question;
    private final Map<String, Integer> optionCount;

    public VotePostHeadLine(Long postId, int likeCount, String question, Map<String, Integer> optionCount){
        super(postId, likeCount, ContentType.VOTE.name());
        this.question = question;
        this.optionCount = optionCount;
    }
}

package com.neo.needeachother.post.domain;


import com.neo.needeachother.category.domain.ContentType;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "star_page_gold_balance_post")
@DiscriminatorValue(value = ContentType.TypeCode.GOLD_BALANCE)
public class GoldBalancePost extends StarPagePost{

    private String question;

    @ElementCollection
    @CollectionTable(name = "gold_balance_post_left_answer", joinColumns = @JoinColumn(name = "post_id"))
    private Set<GoldBalanceLeftAnswer> leftAnswers;

    @ElementCollection
    @CollectionTable(name = "gold_balance_post_right_answer", joinColumns = @JoinColumn(name = "post_id"))
    private Set<GoldBalanceRightAnswer> rightAnswers;

    private boolean isNotVoted(){
        return leftAnswers.isEmpty() && rightAnswers.isEmpty();
    }

    public LeftRightRate calculateOXRate(){
        if(isNotVoted()){
            return new LeftRightRate(0, 0);
        }

        int oCount = leftAnswers.size();
        int xCount = rightAnswers.size();
        int total = oCount + xCount;

        double oRate = (double) oCount / total;
        double xRate = (double) xCount / total;
        return new LeftRightRate(0, 0);
    }


}

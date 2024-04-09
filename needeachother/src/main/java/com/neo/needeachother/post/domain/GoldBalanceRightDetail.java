package com.neo.needeachother.post.domain;

import com.neo.needeachother.common.exception.NEOUnexpectedException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GoldBalanceRightDetail {

    @Column(name = "right_example")
    private String rightExample;

    @ElementCollection
    @CollectionTable(name = "gold_balance_post_right_chooser", joinColumns = @JoinColumn(name = "post_id"))
    private Set<GoldBalanceChooser> rightChooser = new HashSet<>();

    public GoldBalanceRightDetail(String rightExample){
        this.rightExample = rightExample;
        this.rightChooser = new HashSet<>();
    }

    protected boolean isNobodyChosen() {
        return rightChooser.isEmpty();
    }

    protected int getRightAnswersCount() {
        return this.rightChooser.size();
    }

    protected void notChooseYet(String email) {
        if (this.rightChooser.contains(GoldBalanceRightAnswer.of(email))) {
            throw new NEOUnexpectedException("이미 우측 답변을 선택했음.");
        }
    }

    protected void chooseRightAnswer(String email) {
        this.rightChooser.add(GoldBalanceChooser.of(email));
    }

    public static GoldBalanceRightDetail of(String rightExample){
        return new GoldBalanceRightDetail(rightExample);
    }
}

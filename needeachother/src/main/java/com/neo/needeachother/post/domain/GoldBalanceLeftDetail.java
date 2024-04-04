package com.neo.needeachother.post.domain;

import com.neo.needeachother.common.exception.NEOUnexpectedException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GoldBalanceLeftDetail {

    @Column(name = "left_example")
    private String leftExample;

    @ElementCollection
    @CollectionTable(name = "gold_balance_post_left_chooser", joinColumns = @JoinColumn(name = "post_id"))
    private Set<GoldBalanceChooser> leftChooser;

    protected boolean isNobodyChosen() {
        return leftChooser.isEmpty();
    }

    protected int getLeftAnswersCount() {
        return this.leftChooser.size();
    }

    protected void notChooseYet(String email) {
        if (this.leftChooser.contains(GoldBalanceChooser.of(email))) {
            throw new NEOUnexpectedException("이미 좌측 답변을 선택했음.");
        }
    }

    protected void chooseLeftAnswer(String email) {
        this.leftChooser.add(GoldBalanceChooser.of(email));
    }

}

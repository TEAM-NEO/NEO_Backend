package com.neo.needeachother.post.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VoteItem {

    @Id
    @Column(name = "vote_item_id")
    private String id;

    private String OptionText;

    @ElementCollection
    @CollectionTable(name = "vote_option_voter", joinColumns = @JoinColumn(name = "vote_item_id"))
    private Set<Voter> voterSet = new HashSet<>();

    protected VoteItem changeOptionText(String optionText){
        return new VoteItem(this.id, optionText, new HashSet<>());
    }

    protected void vote(Voter voter){
        this.voterSet.add(voter);
    }

    protected boolean nobodyVotedThisOptionYet(){
        return this.voterSet.size() == 0;
    }
}

package com.neo.needeachother.post.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vote_item")
@EqualsAndHashCode
@AllArgsConstructor
@Getter(value = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VoteItem {

    @Id
    @Column(name = "vote_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "option_text")
    private String optionText;

    @ElementCollection
    @CollectionTable(name = "vote_option_voter", joinColumns = @JoinColumn(name = "vote_item_id"))
    private Set<Voter> voterSet = new HashSet<>();

    public VoteItem(String optionText){
        this.optionText = optionText;
    }

    protected VoteItem changeOptionText(String optionText){
        return new VoteItem(this.id, optionText, new HashSet<>());
    }

    protected void vote(Voter voter){
        this.voterSet.add(voter);
    }

    protected boolean nobodyVotedThisOptionYet(){
        return this.voterSet.size() == 0;
    }

    public static VoteItem of(String optionText){
        return new VoteItem(optionText);
    }
}

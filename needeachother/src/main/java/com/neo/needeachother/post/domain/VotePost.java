package com.neo.needeachother.post.domain;

import com.neo.needeachother.category.domain.ContentType;
import com.neo.needeachother.common.exception.NEOUnexpectedException;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "star_page_vote_post")
@DiscriminatorValue(value = ContentType.TypeCode.VOTE)
public class VotePost extends StarPagePost {

    @Column(name = "question")
    private String question;

    @Column(name = "vote_status")
    @Enumerated(value = EnumType.STRING)
    private VoteStatus voteStatus;

    private int timeToLive;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "post_id")
    @OrderColumn(name = "vote_item_order")
    private List<VoteItem> voteItems = new ArrayList<>();

    public void changeQuestion(String email, String question){
        this.canChangeVotePostElement();
        this.isAuthor(email);
        this.question = question;
    }

    public void closeVotePost(){
        this.voteStatus = VoteStatus.CLOSE;
    }

    public void changeOptionTextWithIdx(String email, int idx, String optionText){
        this.canChangeVotePostElement();
        this.isAuthor(email);
        if(this.voteItems.size() <= idx){
            throw new NEOUnexpectedException("투표 항목 인덱스가 범위를 벗어남.");
        }
        this.voteItems.set(idx, this.voteItems.get(idx).changeOptionText(optionText));
    }

    public void voteByOptionIdx(String email, Voter voter, int idx){
        this.canVote();

    }

    private boolean nobodyVotedYet(){
        return this.voteItems.stream()
                .map(VoteItem::nobodyVotedThisOptionYet)
                .reduce(true, (prev, curr) -> prev && curr);
    }

    private void canVote(){
        if(this.voteStatus == VoteStatus.CLOSE){
            throw new NEOUnexpectedException("투표 상태가 닫혀있습니다.");
        }
    }

    private void canChangeVotePostElement(){
        if(this.voteStatus == VoteStatus.CLOSE || !this.nobodyVotedYet()){
            throw new NEOUnexpectedException("투표 상태가 종료되었거나, 한 명이라도 특정 보기에 투표해 변경할 수 없음.");
        }
    }

}

package com.neo.needeachother.post.domain;

import com.neo.needeachother.category.domain.CategoryId;
import com.neo.needeachother.category.domain.ContentType;
import com.neo.needeachother.common.exception.NEOUnexpectedException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "star_page_vote_post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VotePost extends StarPagePost {

    @Column(name = "question")
    private String question;

    @Column(name = "vote_status")
    @Enumerated(value = EnumType.STRING)
    private VoteStatus voteStatus;

    // @Column(name = "time_to_live")
    private int timeToLive;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "post_id")
    @OrderColumn(name = "vote_item_order")
    private List<VoteItem> voteItems = new ArrayList<>();

    public VotePost(CategoryId categoryId, String title, Author author, PostStatus status,
                    String question, VoteStatus voteStatus, int timeToLive, List<VoteItem> voteItems) {
        super(categoryId, title, author, status, PostType.VOTE);
        this.question = question;
        this.voteStatus = voteStatus;
        this.timeToLive = timeToLive;
        this.voteItems = voteItems;
    }

    public void changeQuestion(String email, String question) {
        this.canChangeVotePostElement();
        this.isAuthor(email);
        this.question = question;
    }

    public void closeVotePost() {
        this.voteStatus = VoteStatus.CLOSE;
    }

    public void changeOptionTextWithIdx(String email, int idx, String optionText) {
        this.canChangeVotePostElement();
        this.isAuthor(email);
        this.checkVoteOptionsOOB(idx);
        this.voteItems.set(idx, this.voteItems.get(idx).changeOptionText(optionText));
    }

    public void voteByOptionIdx(String email, Voter voter, int idx) {
        this.canVote();
        this.checkVoteOptionsOOB(idx);
        this.voteItems.get(idx).vote(voter);
    }

    private void checkVoteOptionsOOB(int idx){
        if (this.voteItems.size() <= idx) {
            throw new NEOUnexpectedException("투표 항목 인덱스가 범위를 벗어남.");
        }
    }

    private boolean nobodyVotedYet() {
        return this.voteItems.stream()
                .map(VoteItem::nobodyVotedThisOptionYet)
                .reduce(true, (prev, curr) -> prev && curr);
    }

    private void canVote() {
        if (this.voteStatus == VoteStatus.CLOSE) {
            throw new NEOUnexpectedException("투표 상태가 닫혀있습니다.");
        }
    }

    private void canChangeVotePostElement() {
        if (this.voteStatus == VoteStatus.CLOSE || !this.nobodyVotedYet()) {
            throw new NEOUnexpectedException("투표 상태가 종료되었거나, 한 명이라도 특정 보기에 투표해 변경할 수 없음.");
        }
    }

    public static VotePost of(CategoryId categoryId, String title, Author author, PostStatus status,
                              String question, VoteStatus voteStatus, int timeToLive, List<VoteItem> voteItems) {
        return new VotePost(categoryId, title, author, status, question, voteStatus, timeToLive, voteItems);
    }

}

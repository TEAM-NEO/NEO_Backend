package com.neo.needeachother.post.domain;

import com.neo.needeachother.category.domain.CategoryId;
import com.neo.needeachother.category.domain.ContentType;
import com.neo.needeachother.post.application.dto.PostDetailDto;
import com.neo.needeachother.post.application.dto.VoteAblePostOptionDetailDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@Entity
@Table(name = "star_page_gold_balance_post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GoldBalancePost extends StarPagePost {

    @Column(name = "question")
    private String question;

    @Embedded
    private GoldBalanceLeftDetail leftDetail;

    @Embedded
    private GoldBalanceRightDetail rightDetail;

    @Embedded
    private LeftRightRate leftRightRate;

    public GoldBalancePost(CategoryId categoryId, String title, Author author, PostStatus status,
                           String question, String leftExample, String rightExample){
        super(categoryId, title, author, status, PostType.GOLD_BALANCE);
        this.question = question;
        this.leftDetail = GoldBalanceLeftDetail.of(leftExample);
        this.rightDetail = GoldBalanceRightDetail.of(rightExample);
        this.leftRightRate = this.calculateAnswerRate();
    }

    @Override
    public PostDetailDto toPostDetailDto() {
        Map<String, VoteAblePostOptionDetailDto> voteOptionMap = new HashMap<>();

        voteOptionMap.put("left", new VoteAblePostOptionDetailDto(this.leftDetail.getLeftExample(),
                this.leftDetail.getLeftAnswersCount(), this.leftRightRate.getLeftRate()));
        voteOptionMap.put("right", new VoteAblePostOptionDetailDto(this.rightDetail.getRightExample(),
                this.rightDetail.getRightAnswersCount(), this.leftRightRate.getRightRate()));

        return PostDetailDto.builder()
                .postId(this.getId())
                .categoryId(this.getCategoryId().getValue())
                .title(this.getTitle())
                .authorName(this.getAuthor().getAuthorName())
                .status(this.getStatus().name())
                .likeCount(this.getLikeCount())
                .hostHeart(this.isHostHeart())
                .createdAt(this.getCreatedAt())
                .exposureAt(this.getExposureAt())
                .postType(this.getPostType().name())
                .question(this.getQuestion())
                .options(voteOptionMap)
                .build();
    }

    // 도메인 : 황밸 게시글은 좌측 답변을 선택할 수 있다.
    public void chooseLeftAnswer(String email) {
        alreadyChosenBy(email);
        this.leftDetail.chooseLeftAnswer(email);
        this.leftRightRate = calculateAnswerRate();
    }

    // 도메인 : 황밸 게시글은 우측 답변을 선택할 수 있다.
    public void chooseRightAnswer(String email) {
        alreadyChosenBy(email);
        this.rightDetail.chooseRightAnswer(email);
        this.leftRightRate = calculateAnswerRate();
    }

    // 도메인 : 황밸 게시글은 답변 비율을 계산할 수 있다.
    // 고민.. 보통 chooseLeftAnswer & chooseRightAnswer을 통해 각 Set가 로딩되어 있는 상태라 그냥 Count를 써도 될 듯..?
    private LeftRightRate calculateAnswerRate() {
        if (isNobodyChosen()) {
            return LeftRightRate.of(0, 0);
        }

        int leftCount = this.leftDetail.getLeftAnswersCount();
        int rightCount = this.rightDetail.getRightAnswersCount();
        int total = leftCount + rightCount;

        return convertIntegerRatioBy(getPercentageByCount(leftCount, total),
                getPercentageByCount(rightCount, total));
    }

    private void alreadyChosenBy(String email) {
        this.leftDetail.notChooseYet(email);
        this.rightDetail.notChooseYet(email);
    }

    private boolean isNobodyChosen() {
        return leftDetail.isNobodyChosen() && rightDetail.isNobodyChosen();
    }


    private double getPercentageByCount(int count, int total) {
        return (double) count * 100 / total;
    }

    private LeftRightRate convertIntegerRatioBy(double leftRatio, double rightRatio) {
        if (leftRatio > rightRatio) {
            return LeftRightRate.of((int) Math.ceil(leftRatio), (int) Math.floor(rightRatio));
        } else {
            return LeftRightRate.of((int) Math.floor(leftRatio), (int) Math.ceil(rightRatio));
        }
    }


}

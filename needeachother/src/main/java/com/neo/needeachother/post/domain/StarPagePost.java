package com.neo.needeachother.post.domain;

import com.neo.needeachother.category.domain.CategoryId;
import com.neo.needeachother.common.entity.NEOTimeDefaultEntity;
import com.neo.needeachother.common.exception.NEOUnexpectedException;
import com.neo.needeachother.post.domain.domainservice.PostFeatureUseAbleQualificationService;
import com.neo.needeachother.post.infra.PostStatusConverter;
import com.neo.needeachother.post.infra.PostTypeConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "star_page_post")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class StarPagePost extends NEOTimeDefaultEntity {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @Column(name = "category_id")
    private CategoryId categoryId;

    @Column(name = "post_title")
    private String title;

    @Embedded
    @Column(name = "author_name")
    private Author author;

    @Column(name = "status")
    @Convert(converter = PostStatusConverter.class)
    private PostStatus status;

    @Column(name = "like_count", nullable = false)
    private int likeCount;

    @Column(name = "host_heart", nullable = false)
    private boolean hostHeart;

    @Column(name = "exposure_at")
    private LocalDateTime exposureAt;

    @Column(name = "post_type", nullable = false)
    @Convert(converter = PostTypeConverter.class)
    private PostType postType;

    @ElementCollection
    @CollectionTable(name = "star_page_post_like", joinColumns = @JoinColumn(name = "post_id"))
    private Set<PostLike> likes = new HashSet<>();

    public StarPagePost(CategoryId categoryId, String title, Author author, PostStatus status, PostType postType){
        this.categoryId = categoryId;
        this.title = title;
        this.author = author;
        this.status = status;
        this.likeCount = 0;
        this.hostHeart = false;
        this.postType = postType;
    }

    private boolean isAlreadyLikedBy(String email) {
        return likes.contains(PostLike.of(email));
    }

    private boolean isAlreadyHearted() { return hostHeart; }

    protected void isAuthor(String email) {
        if(!this.author.equals(Author.of("익명", email))){
            throw new NEOUnexpectedException("작성자가 아닙니다.");
        }
    }

    private boolean conditionForExposure(){
        return this.status == PostStatus.OPEN && this.likeCount >= 10;
    }

    public void doLike(PostFeatureUseAbleQualificationService qualificationService,
                       ApplicationEventPublisher applicationEventPublisher,
                       String email) {
        // TODO : LOCKING
        qualificationService.canUseLikeFeature(this.categoryId);
        if (isAlreadyLikedBy(email)) {
            throw new NEOUnexpectedException("이미 좋아요를 누른 글입니다.");
        }
        this.likes.add(PostLike.of(email));
        this.likeCount += 1;
        if(conditionForExposure()){
            applicationEventPublisher.publishEvent(new LikeCountExposureConditionMetEvent(this.id));
        }
    }

    public void cancelLike(PostFeatureUseAbleQualificationService qualificationService,
                           String email) {
        // TODO : LOCKING
        qualificationService.canUseLikeFeature(this.categoryId);
        if (!isAlreadyLikedBy(email)) {
            throw new NEOUnexpectedException("좋야요를 누른 상태가 아닙니다.");
        }
        likes.remove(PostLike.of(email));
        likeCount -= 1;
    }

    public void giveHostHeart(PostFeatureUseAbleQualificationService qualificationService,
                              String email) {
        qualificationService.canUseHostHeartFeature();
        if(isAlreadyHearted()){
            throw new NEOUnexpectedException("호스트 하트를 이미 누른 상태입니다.");
        }
        this.hostHeart = true;
    }

    public void cancelHostHeart(PostFeatureUseAbleQualificationService qualificationService,
                                String email) {
        qualificationService.canUseHostHeartFeature();
        if(!isAlreadyHearted()){
            throw new NEOUnexpectedException("호스트 하트를 이미 누르지 않은 상태입니다.");
        }
        this.hostHeart = false;
    }

    public void changeTitle(String email, String changeTitle){
        isAuthor(email);
        this.title = changeTitle;
    }

    public void delete(String email){
        isAuthor(email);
        this.status = PostStatus.DELETED;
        // TODO : 삭제 이벤트 발행 -> 소속된 댓글 모두 삭제로 변경
    }

    public void restore(String email){
        isAuthor(email);
        if(this.status == PostStatus.DELETED){
            this.status = PostStatus.OPEN;
        }
        // TODO : 생성,복귀 이벤트 발행 -> 소속된 댓글 모두 복구로 변경
    }

    public void selectToPopularPost(){
        this.status = PostStatus.MAIN_EXPOSED;
        this.exposureAt = LocalDateTime.now();
    }
}

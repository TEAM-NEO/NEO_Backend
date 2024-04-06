package com.neo.needeachother.post.domain;

import com.neo.needeachother.category.domain.CategoryId;
import com.neo.needeachother.common.entity.NEOTimeDefaultEntity;
import com.neo.needeachother.common.exception.NEOUnexpectedException;
import com.neo.needeachother.post.domain.domainservice.PostFeatureUseAbleQualificationService;
import com.neo.needeachother.post.infra.PostStatusConverter;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "star_page_post")
@DiscriminatorColumn(name = "post_type")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class StarPagePost extends NEOTimeDefaultEntity {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private CategoryId categoryId;

    @Column(name = "post_title")
    private String title;

    @Embedded
    @Column(name = "author_name")
    private Author author;

    @Column(name = "status")
    @Convert(converter = PostStatusConverter.class)
    private PostStatus status;

    @Column(name = "like_count")
    private int likeCount;

    @Column(name = "host_heart")
    private boolean hostHeart;

    @ElementCollection
    @CollectionTable(name = "star_page_post_like", joinColumns = @JoinColumn(name = "post_id"))
    private Set<PostLike> likes = new HashSet<>();

    private boolean isAlreadyLikedBy(String email) {
        return likes.contains(PostLike.of(email));
    }

    private boolean isAlreadyHearted() { return hostHeart; }

    protected void isAuthor(String email) {
        if(!this.author.equals(Author.of("익명", email))){
            throw new NEOUnexpectedException("작성자가 아닙니다.");
        }
    }

    public void doLike(PostFeatureUseAbleQualificationService qualificationService,
                       String email) {
        // TODO : LOCKING
        qualificationService.canUseLikeFeature(this.categoryId);
        if (isAlreadyLikedBy(email)) {
            throw new NEOUnexpectedException("이미 좋아요를 누른 글입니다.");
        }
        likes.add(PostLike.of(email));
        likeCount += 1;
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
        // 삭제 이벤트 발행 -> 소속된 댓글 모두 삭제로 변경
    }

    public void reOpen(String email){
        isAuthor(email);
        this.status = PostStatus.OPEN;
        // 생성,복귀 이벤트 발행 -> 소속된 댓글 모두 복구로 변경
    }

    public void selectToPopularPost(){
        this.status = PostStatus.MAIN_EXPOSED;
    }
}

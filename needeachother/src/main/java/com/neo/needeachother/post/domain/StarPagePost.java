package com.neo.needeachother.post.domain;

import com.neo.needeachother.category.domain.CategoryId;
import com.neo.needeachother.common.entity.NEOTimeDefaultEntity;
import com.neo.needeachother.common.exception.NEOUnexpectedException;
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

    private void canLike(){

    }

    private boolean isAlreadyLikedBy(String email) {
        return likes.contains(PostLike.of(email));
    }

    public void doLike(String email) {
        // Locking 필요
        if (isAlreadyLikedBy(email)) {
            throw new NEOUnexpectedException("이미 좋아요를 누른 글입니다.");
        }
        likes.add(PostLike.of(email));
        likeCount += 1;
    }

    public void cancelLike(String email) {
        // Locking 필요
        if (!isAlreadyLikedBy(email)) {
            throw new NEOUnexpectedException("좋야요를 누른 상태가 아닙니다.");
        }
        likes.remove(PostLike.of(email));
        likeCount -= 1;
    }

    public void giveHostHeart() {
    }

    public void cancelHostHeart() {
    }

    public void changeTitle(){
    }

    public void delete(){}

    public void reOpen(){}

    public void selectToPopularPost(){}
}

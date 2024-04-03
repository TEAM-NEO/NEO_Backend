package com.neo.needeachother.post.domain;

import com.neo.needeachother.category.domain.CategoryId;
import com.neo.needeachother.common.entity.NEOTimeDefaultEntity;
import com.neo.needeachother.common.exception.NEOUnexpectedException;
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

    private String title;

    private String author;

    private PostStatus status;

    private int likeCount;

    @ElementCollection
    @CollectionTable(name = "star_page_post_like", joinColumns = @JoinColumn(name = "post_id"))
    private Set<PostLike> likes = new HashSet<>();

    private boolean isAlreadyLikedBy(String email){
        return likes.contains(PostLike.of(email));
    }

    public void doLike(String email){
        if(isAlreadyLikedBy(email)){
            throw new NEOUnexpectedException("이미 좋아요를 누른 글입니다.");
        }
        likes.add(PostLike.of(email));
    }

    public void cancelLike(String email){

    }
}

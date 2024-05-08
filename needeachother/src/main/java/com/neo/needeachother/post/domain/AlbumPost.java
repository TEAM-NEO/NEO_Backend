package com.neo.needeachother.post.domain;

import com.neo.needeachother.category.domain.CategoryId;
import com.neo.needeachother.category.domain.ContentType;
import com.neo.needeachother.post.application.dto.PostDetailDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "star_page_album_post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlbumPost extends StarPagePost{

    @Embedded
    private AlbumImage image;

    public AlbumPost(CategoryId categoryId, String title, Author author, PostStatus status,
                     AlbumImage albumImage){
        super(categoryId, title, author, status, PostType.ALBUM);
        this.image = albumImage;
    }

    public void changeImage(String email, String path){
        this.isAuthor(email);
        this.image = image.changeImage(path);
    }

    public AlbumPost of(CategoryId categoryId, String title, Author author, PostStatus status,
                        AlbumImage albumImage){
        return new AlbumPost(categoryId, title, author, status, albumImage);
    }

    @Override
    public PostDetailDto toPostDetailDto() {
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
                .representativeImage(this.image.getPath())
                .build();
    }
}

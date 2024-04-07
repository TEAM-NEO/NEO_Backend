package com.neo.needeachother.post.domain;

import com.neo.needeachother.category.domain.CategoryId;
import com.neo.needeachother.category.domain.ContentType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "star_page_album_post")
@DiscriminatorValue(value = ContentType.TypeCode.ALBUM)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlbumPost extends StarPagePost{

    @Embedded
    private AlbumImage image;

    public AlbumPost(CategoryId categoryId, String title, Author author, PostStatus status,
                     AlbumImage albumImage){
        super(categoryId, title, author, status);
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
}

package com.neo.needeachother.post.domain;

import com.neo.needeachother.category.domain.ContentType;
import jakarta.persistence.*;

@Entity
@Table(name = "star_page_album_post")
@DiscriminatorValue(value = ContentType.TypeCode.ALBUM)
public class AlbumPost extends StarPagePost{

    @Embedded
    private AlbumImage image;

    public void changeImage(String path){
        this.image = image.changeImage(path);
    }
}

package com.neo.needeachother.post.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlbumImage {

    @Getter(value = AccessLevel.PROTECTED)
    @Column(name = "image_path")
    private String path;

    protected AlbumImage changeImage(String path){
        return new AlbumImage(path);
    }

    public static AlbumImage of(String path){
        return new AlbumImage(path);
    }
}

package com.neo.needeachother.starpage.domain.dto;

import com.neo.needeachother.category.domain.ContentType;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class ImagePostHeadLine extends StarPageHeadLine {
    private final String representativeImage;

    public ImagePostHeadLine(Long postId, int likeCount, String representativeImage){
        super(postId, likeCount, ContentType.ALBUM.name());
        this.representativeImage = representativeImage;
    }
}

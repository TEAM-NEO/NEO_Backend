package com.neo.needeachother.starpage.domain.dto;

import com.neo.needeachother.category.domain.ContentType;
import com.neo.needeachother.starpage.application.dto.StarPageLayoutViewTileData;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class CommonPostHeadLine extends StarPageHeadLine {
    private final String author;
    private final String title;
    private final String representativeImage;

    public CommonPostHeadLine(Long postId, int likeCount, String author, String title, String representativeImage){
        super(postId, likeCount, ContentType.COMMON.name());
        this.author = author;
        this.title = title;
        this.representativeImage = representativeImage;
    }

}

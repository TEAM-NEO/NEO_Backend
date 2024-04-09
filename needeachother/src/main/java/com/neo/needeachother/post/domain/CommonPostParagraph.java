package com.neo.needeachother.post.domain;

import com.neo.needeachother.post.infra.CommonPostParagraphTypeConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommonPostParagraph {

    @Lob
    private String body;

    @Column(name = "type")
    @Convert(converter = CommonPostParagraphTypeConverter.class)
    private CommonPostParagraphType commonPostContentType;

    public static CommonPostParagraph ofTextParagraph(String text){
        return new CommonPostParagraph(text, CommonPostParagraphType.TEXT);
    }

    public static CommonPostParagraph ofImageParagraph(String imgPath){
        return new CommonPostParagraph(imgPath, CommonPostParagraphType.IMAGE);
    }
}

package com.neo.needeachother.post.domain;

import com.neo.needeachother.post.application.dto.CommonPostParagraphDto;
import com.neo.needeachother.post.infra.CommonPostParagraphTypeConverter;
import jakarta.persistence.*;
import lombok.*;

@Getter
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

    public static CommonPostParagraph of(String textOrPath, String type){
        return new CommonPostParagraph(textOrPath, CommonPostParagraphType.valueOf(type));
    }

    public static CommonPostParagraph ofTextParagraph(String text){
        return new CommonPostParagraph(text, CommonPostParagraphType.TEXT);
    }

    public static CommonPostParagraph ofImageParagraph(String imgPath){
        return new CommonPostParagraph(imgPath, CommonPostParagraphType.IMAGE);
    }

    public CommonPostParagraphDto toDto(){
        return CommonPostParagraphDto.builder()
                .body(this.body)
                .paragraphType(this.commonPostContentType.name())
                .build();
    }
}

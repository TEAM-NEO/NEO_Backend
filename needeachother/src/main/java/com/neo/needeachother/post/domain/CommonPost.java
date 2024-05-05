package com.neo.needeachother.post.domain;

import com.neo.needeachother.category.domain.CategoryId;
import com.neo.needeachother.category.domain.ContentType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Entity
@Table(name = "star_page_common_post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommonPost extends StarPagePost{

    @Column(name = "main_image_path")
    private String representativeImage;

    @ElementCollection
    @CollectionTable(name = "star_page_common_post_detail", joinColumns = @JoinColumn(name = "post_id"))
    @OrderColumn(name = "content_order")
    private List<CommonPostParagraph> commonPostContents = new ArrayList<>();

    public CommonPost(CategoryId categoryId, String title, Author author, PostStatus status, List<CommonPostParagraph> paragraphs){
        super(categoryId, title, author, status, PostType.COMMON);
        this.commonPostContents = new ArrayList<>(paragraphs);
        this.representativeImage = this.commonPostContents.stream()
                .filter(paragraph -> paragraph.getCommonPostContentType() == CommonPostParagraphType.IMAGE)
                .findFirst()
                .map(CommonPostParagraph::getBody)
                .orElse(null);
    }

    public void writeWithParagraph(List<CommonPostParagraph> paragraphs){
        this.commonPostContents = paragraphs;
    }

    public void changeCommonPostContents(List<CommonPostParagraph> modifiedContents){
        commonPostContents.clear();
        commonPostContents = modifiedContents;
    }
}

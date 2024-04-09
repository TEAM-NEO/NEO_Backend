package com.neo.needeachother.post.domain;

import com.neo.needeachother.category.domain.CategoryId;
import com.neo.needeachother.category.domain.ContentType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "star_page_common_post")
@DiscriminatorValue(value = ContentType.TypeCode.COMMON)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommonPost extends StarPagePost{

    @ElementCollection
    @CollectionTable(name = "star_page_common_post_detail", joinColumns = @JoinColumn(name = "post_id"))
    @OrderColumn(name = "content_order")
    private List<CommonPostParagraph> commonPostContents = new ArrayList<>();

    public CommonPost(CategoryId categoryId, String title, Author author, PostStatus status){
        super(categoryId, title, author, status);
    }

    public void writeWithParagraph(List<CommonPostParagraph> paragraphs){
        this.commonPostContents = paragraphs;
    }

    public void changeCommonPostContents(List<CommonPostParagraph> modifiedContents){
        commonPostContents.clear();
        commonPostContents = modifiedContents;
    }
}

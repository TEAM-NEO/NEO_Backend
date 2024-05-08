package com.neo.needeachother.post.domain;

import com.neo.needeachother.category.domain.CategoryId;
import com.neo.needeachother.post.application.dto.PostDetailDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
                .paragraph(this.commonPostContents.stream().map(CommonPostParagraph::toDto).toList())
                .representativeImage(this.representativeImage)
                .build();
    }
}

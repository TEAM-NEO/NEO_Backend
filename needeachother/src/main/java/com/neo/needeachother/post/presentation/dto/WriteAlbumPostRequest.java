package com.neo.needeachother.post.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class WriteAlbumPostRequest {
    private String title;
    private String authorName;
    private String authorEmail;
    private String categoryId;
    private String imagePath;
}

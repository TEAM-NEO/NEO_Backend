package com.neo.needeachother.post.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LikeCountExposureConditionMetEvent {
    private final Long postId;
}

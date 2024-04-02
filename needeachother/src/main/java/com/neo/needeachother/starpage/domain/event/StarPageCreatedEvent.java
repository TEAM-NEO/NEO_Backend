package com.neo.needeachother.starpage.domain.event;

import com.neo.needeachother.starpage.domain.StarPageId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StarPageCreatedEvent {
    private final StarPageId createdStarPageId;
}

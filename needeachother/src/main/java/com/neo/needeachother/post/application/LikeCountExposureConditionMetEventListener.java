package com.neo.needeachother.post.application;

import com.neo.needeachother.post.domain.LikeCountExposureConditionMetEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class LikeCountExposureConditionMetEventListener {

    private final ExposurePostToStarPageMainService exposurePostToStarPageMainService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onApplicationEvent(LikeCountExposureConditionMetEvent likeCountExposureConditionMetEvent) {
        exposurePostToStarPageMainService.exposureMain(likeCountExposureConditionMetEvent.getPostId());
    }
}

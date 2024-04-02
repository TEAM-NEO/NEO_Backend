package com.neo.needeachother.category.application;

import com.neo.needeachother.starpage.domain.event.StarPageCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class StarPageCreatedEventListener {

    private final InitialCategoryCreationService initialCategoryCreationService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onApplicationEvent(StarPageCreatedEvent starPageCreatedEvent) {
        initialCategoryCreationService.createInitialCategory(starPageCreatedEvent.getCreatedStarPageId());
    }
}

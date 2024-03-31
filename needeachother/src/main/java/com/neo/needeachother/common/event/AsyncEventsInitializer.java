package com.neo.needeachother.common.event;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;

@Component
public class AsyncEventsInitializer {

    @PostConstruct
    public void init() {
        Events.init(Executors.newFixedThreadPool(10));
    }

    @PreDestroy
    public void close() {
        Events.close();
    }

}

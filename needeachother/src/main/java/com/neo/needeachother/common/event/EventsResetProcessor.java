package com.neo.needeachother.common.event;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(0)
@Component
public class EventsResetProcessor {

    private ThreadLocal<Integer> nestedCount = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return new Integer(0);
        }
    };

    @Around("@target(org.springframework.stereotype.Service) && within(com.neo.needeachother.starpage..*)")
    public Object doReset(ProceedingJoinPoint joinPoint) throws Throwable {
        nestedCount.set(nestedCount.get() + 1);
        try {
            return joinPoint.proceed();
        } finally {
            nestedCount.set(nestedCount.get() - 1);
            if (nestedCount.get() == 0) {
                Events.reset();
            }
        }
    }

}

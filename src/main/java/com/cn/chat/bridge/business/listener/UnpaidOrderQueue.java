
package com.cn.chat.bridge.business.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RDelayedQueue;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * The type Unpaid order queue.
 *
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class UnpaidOrderQueue {

    private final RDelayedQueue<String> delayedQueue;


    public void addOrder(final String orderNo) {
        delayedQueue.offer(orderNo, 290, TimeUnit.SECONDS);
    }
}

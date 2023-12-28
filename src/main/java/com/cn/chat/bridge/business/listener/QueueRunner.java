
package com.cn.chat.bridge.business.listener;

/**
 * The type Queue runner.
 *
 */
/*@Component
@RequiredArgsConstructor
@Slf4j
public class QueueRunner implements CommandLineRunner {


    *//*private final RBlockingDeque<String> blockingFairQueue;

    private final OrderRepository orderRepository;

    @Override
    public void run(String... args) {
        *//**//*new Thread(() -> {
            while (true) {
                String orderNo;
                try {
                    //获取订单号
                    orderNo = blockingFairQueue.take();
                } catch (Exception e) {
                    continue;
                }
                orderRepository.getIdByIdAndState()
                Order superOrder = orderMapper.selectOne(new QueryWrapper<Order>()
                        .lambda()
                        .eq(Order::getId, orderNo)
                        .eq(Order::getState, 1)
                        .select(Order::getId)
                );
                if (superOrder == null) {
                    orderMapper.updateById(new Order()
                            .setId(orderNo)
                            .setState(2)
                            .setReasonFailure(ExceptionMessages.NOT_PAID_FOR_LONG_TIME)
                    );
                }
            }
        }).start();
    }*//*
}*/

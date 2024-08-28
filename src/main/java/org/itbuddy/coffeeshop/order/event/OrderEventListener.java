package org.itbuddy.coffeeshop.order.event;


import lombok.RequiredArgsConstructor;
import org.itbuddy.coffeeshop.common.KafkaMessagePublisher;
import org.itbuddy.coffeeshop.common.KafkaTopic;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class OrderEventListener {
    private final KafkaMessagePublisher kafkaMessagePublisher;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void publishOrderEvent(OrderEvent event){
        kafkaMessagePublisher.publish(
            KafkaTopic.ORDER,
            event.orderDto().getId().toString(),
            event.orderDto()
        );
    }


}

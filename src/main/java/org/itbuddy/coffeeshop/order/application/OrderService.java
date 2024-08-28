package org.itbuddy.coffeeshop.order.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.itbuddy.coffeeshop.common.KafkaMessagePublisher;
import org.itbuddy.coffeeshop.common.KafkaTopic;
import org.itbuddy.coffeeshop.config.distributionlock.DistributedLock;
import org.itbuddy.coffeeshop.menu.domain.MenuEntity;
import org.itbuddy.coffeeshop.menu.domain.MenuRepository;
import org.itbuddy.coffeeshop.order.domain.OrderEntity;
import org.itbuddy.coffeeshop.order.domain.OrderRepository;
import org.itbuddy.coffeeshop.user.domain.UserEntity;
import org.itbuddy.coffeeshop.user.domain.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final UserRepository userRepository;

    private final KafkaMessagePublisher kafkaMessagePublisher;

    @Transactional
    @DistributedLock("#userId")
    public OrderDto order(Long userId, Long menuId) {

        final MenuEntity menu = menuRepository.findById(menuId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 메뉴입니다."));

        final UserEntity user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 사용자입니다."));
        user.usePoint(menu.getPrice());
        userRepository.save(user);

        final OrderEntity order = OrderEntity.ofEntityByOrder(userId, menu);
        final OrderDto orderDto = orderRepository.save(order).toDto(menu);

        kafkaMessagePublisher.publish(KafkaTopic.ORDER, orderDto.getId().toString(), orderDto);
        return OrderDto.ofDtoByOrder(order.getId(), menu);
    }

    void sendKafka(){
        log.info("send message to kafka");
    }

}

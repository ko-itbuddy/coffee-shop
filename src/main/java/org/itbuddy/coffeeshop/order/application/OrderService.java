package org.itbuddy.coffeeshop.order.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.itbuddy.coffeeshop.config.distributionlock.DistributedLock;
import org.itbuddy.coffeeshop.menu.domain.MenuEntity;
import org.itbuddy.coffeeshop.menu.domain.MenuRepository;
import org.itbuddy.coffeeshop.order.domain.OrderEntity;
import org.itbuddy.coffeeshop.order.domain.OrderRepository;
import org.itbuddy.coffeeshop.order.event.OrderEvent;
import org.itbuddy.coffeeshop.user.domain.UserEntity;
import org.itbuddy.coffeeshop.user.domain.UserRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final UserRepository userRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    @DistributedLock("#userId")
    public OrderDto order(Long userId, Long menuId) {

        final MenuEntity menu = menuRepository.findById(menuId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 메뉴입니다."));

        final UserEntity user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 사용자입니다."));
        user.usePoint(menu.getPrice());
        userRepository.save(user);

        final OrderEntity order = OrderEntity.ofEntityByOrder(userId, menu);
        final OrderDto orderDto = orderRepository.save(order).toDto(menu);

        applicationEventPublisher.publishEvent(new OrderEvent(orderDto));
        return OrderDto.ofDtoByOrder(order.getId(), menu);
    }

}

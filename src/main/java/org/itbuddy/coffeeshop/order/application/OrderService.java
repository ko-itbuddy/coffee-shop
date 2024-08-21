package org.itbuddy.coffeeshop.order.application;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final UserRepository userRepository;

    @Transactional
    @DistributedLock("#userId")
    public OrderDto order(Long userId, Long menuId) {

        MenuEntity menu = menuRepository.findById(menuId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 메뉴입니다."));

        UserEntity user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 사용자입니다."));
        user.usePoint(menu.getPrice());
        userRepository.save(user);

        OrderEntity order = OrderEntity.builder()
                                       .userId(userId)
                                       .menuId(menuId)
                                       .build();
        orderRepository.save(order);

        return OrderDto.ofDtoByOrder(order.getId(), menu);
    }


}

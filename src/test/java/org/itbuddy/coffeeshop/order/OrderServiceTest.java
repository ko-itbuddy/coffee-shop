package org.itbuddy.coffeeshop.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.itbuddy.coffeeshop.common.KafkaMessagePublisher;
import org.itbuddy.coffeeshop.config.TestContainerConfig;
import org.itbuddy.coffeeshop.menu.domain.MenuEntity;
import org.itbuddy.coffeeshop.menu.domain.MenuRepository;
import org.itbuddy.coffeeshop.order.application.OrderDto;
import org.itbuddy.coffeeshop.order.application.OrderService;
import org.itbuddy.coffeeshop.order.domain.OrderRepository;
import org.itbuddy.coffeeshop.order.event.OrderEventListener;
import org.itbuddy.coffeeshop.user.domain.UserEntity;
import org.itbuddy.coffeeshop.user.domain.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ActiveProfiles("test")
@SpringBootTest
@ExtendWith(TestContainerConfig.class)
@ContextConfiguration(initializers = TestContainerConfig.IntegrationTestInitializer.class)
public class OrderServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MenuRepository menuRepository;


    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaMessagePublisher kafkaMessagePublisher;


    @BeforeEach
    void init() throws Exception {

        UserEntity user = createUserEntity("홍길동", 10000);
        userRepository.save(user);
        MenuEntity menu1 = createMenuEntity("아메리카노", 1500);
        MenuEntity menu2 = createMenuEntity("바가지 아메리카노", 10000);
        MenuEntity menu3 = createMenuEntity("더 바가지 아메리카노", 20000);
        menuRepository.saveAll(List.of(menu1, menu2, menu3));
    }

    @AfterEach
    void tearDown() throws Exception {
        orderRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
        menuRepository.deleteAllInBatch();
    }

    @Nested
    @DisplayName("3. 커피 주문/결제 하기")
    class PostOrder {

        @Test
        @DisplayName("정상")
        void success() throws Exception {
            // given
            UserEntity user = userRepository.findAll().get(0);
            MenuEntity menu = menuRepository.findAll()
                                            .stream()
                                            .filter(vo -> vo.getPrice().equals(1500))
                                            .findFirst()
                                            .orElseThrow(() -> new IllegalArgumentException(
                                                "1500원 짜리 메뉴 미존재"));

            // when
            OrderDto order = orderService.order(user.getId(), menu.getId());
            // then
            assertThat(order).extracting("menu.name", "menu.price")
                             .containsExactly(
                                 "아메리카노", 1500
                             );
            UserEntity payedUser = userRepository.findById(user.getId())
                                                 .orElseThrow(() -> new IllegalArgumentException(
                                                     "사용자가 없습니다."));
            assertThat(payedUser).extracting("id", "point")
                                 .containsExactly(user.getId(), 8500);

        }

        @Test
        @DisplayName("사용자 포인트에 딱 맞는 금액을 주문")
        void fitUserPoint() throws Exception {
            // given
            UserEntity user = userRepository.findAll().get(0);
            MenuEntity menu = menuRepository.findAll()
                                            .stream()
                                            .filter(vo -> vo.getPrice().equals(10000))
                                            .findFirst()
                                            .orElseThrow(() -> new IllegalArgumentException(
                                                "10000 짜리 메뉴 미존재"));

            // when
            OrderDto order = orderService.order(user.getId(), menu.getId());
            // then
            assertThat(order).extracting("menu.name", "menu.price")
                             .containsExactly(
                                 "바가지 아메리카노", 10000
                             );
            UserEntity payedUser = userRepository.findById(user.getId())
                                                 .orElseThrow(() -> new IllegalArgumentException(
                                                     "사용자가 없습니다."));
            assertThat(payedUser).extracting("id", "point")
                                 .containsExactly(user.getId(), 0);

        }

        @Test
        @DisplayName("사용자 포인트보다 큰 금액을 주문")
        void notEnoughUserPoint() throws Exception {
            // given
            UserEntity user = userRepository.findAll().get(0);
            MenuEntity menu = menuRepository.findAll()
                                            .stream()
                                            .filter(vo -> vo.getPrice().equals(20000))
                                            .findFirst()
                                            .orElseThrow(() -> new IllegalArgumentException(
                                                "20000 짜리 메뉴 미존재"));

            // when
            // then

            assertThatThrownBy(() -> orderService.order(user.getId(), menu.getId()))
                .isInstanceOf(IllegalArgumentException.class);
            UserEntity payedUser = userRepository.findById(user.getId())
                                                 .orElseThrow(() -> new IllegalArgumentException(
                                                     "사용자가 없습니다."));
            assertThat(payedUser).extracting("id", "point")
                                 .containsExactly(user.getId(), 10000);
        }

        @Test
        @DisplayName("한명의 사용자가 한번에 6번 연속 주문")
        void order6time() throws Exception {
            // given
            UserEntity user = userRepository.findAll().get(0);
            MenuEntity menu = menuRepository.findAll()
                                            .stream()
                                            .filter(vo -> vo.getPrice().equals(1500))
                                            .findFirst()
                                            .orElseThrow(() -> new IllegalArgumentException(
                                                "1500원 짜리 메뉴 미존재"));

            // when
            CountDownLatch latch = new CountDownLatch(6);
            ExecutorService executorService = Executors.newFixedThreadPool(32);

            makeCountDownLatch(latch, executorService, user.getId(), menu.getId(), 6);

            latch.await();
            // then

            UserEntity payedUser = userRepository.findById(user.getId())
                                                 .orElseThrow(() -> new IllegalArgumentException(
                                                     "사용자가 없습니다."));
            assertThat(payedUser).extracting("id", "point")
                                 .containsExactly(user.getId(), 1000);

        }


    }


    private void makeCountDownLatch(CountDownLatch latch, ExecutorService executorService,
        Long userId, Long menuId, long repeat) {

        for (int i = 0; i < repeat; i++) {
            int finalI = i;
            executorService.submit(() -> {
                try {
                    orderService.order(userId, menuId);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    latch.countDown();
                }
            });
        }
    }


    private MenuEntity createMenuEntity(String name, Integer price) {
        return MenuEntity.builder()
                         .name(name)
                         .price(price)
                         .build();
    }

    private UserEntity createUserEntity(String name, Integer point) {
        return UserEntity.builder()
                         .name(name)
                         .point(point)
                         .build();
    }

}

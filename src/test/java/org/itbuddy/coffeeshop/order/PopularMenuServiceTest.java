package org.itbuddy.coffeeshop.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.itbuddy.coffeeshop.config.TestContainerConfig;
import org.itbuddy.coffeeshop.menu.domain.MenuEntity;
import org.itbuddy.coffeeshop.order.application.PopularMenuDto;
import org.itbuddy.coffeeshop.order.application.PopularMenuService;
import org.itbuddy.coffeeshop.order.domain.OrderEntity;
import org.itbuddy.coffeeshop.order.domain.OrderRepository;
import org.itbuddy.coffeeshop.user.domain.UserEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.auditing.AuditingHandler;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ActiveProfiles("test")
@SpringBootTest
@ExtendWith(TestContainerConfig.class)
@ContextConfiguration(initializers = TestContainerConfig.IntegrationTestInitializer.class)
public class PopularMenuServiceTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    PopularMenuService popularMenuService;

    @MockBean
    private DateTimeProvider dateTimeProvider;

    @SpyBean
    private AuditingHandler handler;


    @BeforeEach
    void init() throws Exception {
        MockitoAnnotations.openMocks(this);
        handler.setDateTimeProvider(dateTimeProvider);
        LocalDateTime baseDateTime = LocalDateTime.of(2024, 8, 31, 0, 0, 0);

        LocalDateTime minus7Days = baseDateTime.minusDays(7);
        Mockito.when(dateTimeProvider.getNow()).thenReturn(Optional.of(minus7Days));
        orderRepository.saveAll(makeOrderList(1L, 1L, "아메리카노", 10));
        orderRepository.saveAll(makeOrderList(1L, 2L, "라떼", 5));
        orderRepository.saveAll(makeOrderList(1L, 3L, "아이스티", 1));

        LocalDateTime minus3Days = baseDateTime.minusDays(3);
        Mockito.when(dateTimeProvider.getNow()).thenReturn(Optional.of(minus3Days));
        orderRepository.saveAll(makeOrderList(1L, 1L, "아메리카노", 10));
        orderRepository.saveAll(makeOrderList(1L, 2L, "라떼", 5));
        orderRepository.saveAll(makeOrderList(1L, 3L, "아이스티", 1));

        Mockito.when(dateTimeProvider.getNow()).thenReturn(Optional.of(baseDateTime));
        orderRepository.saveAll(makeOrderList(1L, 1L, "아메리카노", 10));
        orderRepository.saveAll(makeOrderList(1L, 2L, "라떼", 5));
        orderRepository.saveAll(makeOrderList(1L, 3L, "아이스티", 1));

    }

    @AfterEach
    void tearDown() throws Exception {
        orderRepository.deleteAllInBatch();
    }

    @Nested
    @DisplayName("4. 인기 메뉴 목록 조회")
    class GetPopularMenus {

        @Test
        @DisplayName("정상 요청")
        void success() {
            // given
            LocalDateTime baseDateTime = LocalDateTime.of(2024, 8, 31, 0, 0, 0);
            // when
            List<PopularMenuDto> popularMenuDtos = popularMenuService.getPopularMenuIn7Days(
                baseDateTime.toLocalDate());

            // then
            assertThat(popularMenuDtos).hasSize(3)
                                       .extracting("id", "name", "orderCnt")
                                       .containsExactly(
                                           tuple(1L, "아메리카노", 20L),
                                           tuple(2L, "라떼", 10L),
                                           tuple(3L, "아이스티", 2L)
                                       );

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

    private OrderEntity createOrderEntity(Long userId, Long menuId, String menuName) {
        return OrderEntity.builder()
                          .userId(userId)
                          .menuId(menuId)
                          .menuName(menuName)
                          .build();
    }

    private List<OrderEntity> makeOrderList(Long userId, Long menuId, String menuName, int repeat) {
        List<OrderEntity> result = new ArrayList<>();
        for (int i = 0; i < repeat; i++) {
            result.add(createOrderEntity(userId, menuId, menuName));
        }
        return result;
    }

}

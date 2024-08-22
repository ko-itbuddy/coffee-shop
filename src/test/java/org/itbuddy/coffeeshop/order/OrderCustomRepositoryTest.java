package org.itbuddy.coffeeshop.order;

import static org.assertj.core.groups.Tuple.tuple;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.itbuddy.coffeeshop.config.TestContainerConfig;
import org.itbuddy.coffeeshop.order.application.PopularMenuDto;
import org.itbuddy.coffeeshop.order.domain.OrderCustomRepository;
import org.itbuddy.coffeeshop.order.domain.OrderEntity;
import org.itbuddy.coffeeshop.order.domain.OrderRepository;
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


@Slf4j
@ActiveProfiles("test")
@SpringBootTest
@ExtendWith(TestContainerConfig.class)
@ContextConfiguration(initializers = TestContainerConfig.IntegrationTestInitializer.class)
public class OrderCustomRepositoryTest {

    @Autowired
    private OrderCustomRepository orderCustomRepository;

    @Autowired
    private OrderRepository orderRepository;

    @MockBean
    private DateTimeProvider dateTimeProvider;

    @SpyBean
    private AuditingHandler handler;


    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        handler.setDateTimeProvider(dateTimeProvider);
    }


    @Nested
    @DisplayName("7일간 인기 메뉴 조회")
    class GetPopularMenuIn7days {

        @BeforeEach
        void init(){
            LocalDateTime baseDateTime = LocalDateTime.of(2024, 8, 31, 0, 0, 0);

            log.info("### minus7Days ###");
            LocalDateTime minus7Days = baseDateTime.minusDays(7);
            Mockito.when(dateTimeProvider.getNow()).thenReturn(Optional.of(minus7Days));
            orderRepository.saveAll(makeOrderList(1L, 1L, "아메리카노", 10));
            orderRepository.saveAll(makeOrderList(1L, 2L, "라떼", 5));
            orderRepository.saveAll(makeOrderList(1L, 3L, "아이스티", 1));

            log.info("### minus3Days ###");
            LocalDateTime minus3Days = baseDateTime.minusDays(3);
            Mockito.when(dateTimeProvider.getNow()).thenReturn(Optional.of(minus3Days));
            orderRepository.saveAll(makeOrderList(1L, 1L, "아메리카노", 10));
            orderRepository.saveAll(makeOrderList(1L, 2L, "라떼", 5));
            orderRepository.saveAll(makeOrderList(1L, 3L, "아이스티", 1));

            log.info("### baseDateTime ###");
            Mockito.when(dateTimeProvider.getNow()).thenReturn(Optional.of(baseDateTime));
            orderRepository.saveAll(makeOrderList(1L, 1L, "아메리카노", 10));
            orderRepository.saveAll(makeOrderList(1L, 2L, "라떼", 5));
            orderRepository.saveAll(makeOrderList(1L, 3L, "아이스티", 1));

        }

        @Test
        @DisplayName("7일간 조회")
        void success() {
            // given
            LocalDateTime baseDateTime = LocalDateTime.of(2024, 8, 31, 0, 0, 0);

            // when
            List<PopularMenuDto> popularMenuDtos = orderCustomRepository.findPopularMenuIn7Days(
                baseDateTime.toLocalDate());

            // then
            Assertions.assertThat(popularMenuDtos)
                .hasSize(3)
                      .extracting("id", "name", "orderCnt")
                      .containsExactly(
                          tuple(1L, "아메리카노", 20L),
                          tuple(2L, "라떼", 10L),
                          tuple(3L, "아이스티", 2L)
                      );
        }
    }

    private List<OrderEntity> makeOrderList(Long userId, Long menuId, String menuName, int repeat) {
        List<OrderEntity> result = new ArrayList<>();
        for (int i = 0; i < repeat; i++) {
            result.add(createOrderEntity(userId, menuId, menuName));
        }
        return result;
    }

    private OrderEntity createOrderEntity(Long userId, Long menuId, String menuName) {
        return OrderEntity.builder()
                          .userId(userId)
                          .menuId(menuId)
                          .menuName(menuName)
                          .build();
    }


}

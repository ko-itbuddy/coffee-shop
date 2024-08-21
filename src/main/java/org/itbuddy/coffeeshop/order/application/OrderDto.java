package org.itbuddy.coffeeshop.order.application;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.itbuddy.coffeeshop.menu.application.MenuDto;
import org.itbuddy.coffeeshop.menu.domain.MenuEntity;

@Getter
@Schema(description = "주문")
public class OrderDto {

    @Schema(description = "주문 ID")
    private final Long id;

    @Schema(description = "주문 메뉴")
    private final MenuDto menu;

    @Builder
    private OrderDto(Long id, MenuDto menu) {
        this.id = id;
        this.menu = menu;
    }

    public static OrderDto ofDtoByOrder(Long orderId, MenuEntity menu) {
        return OrderDto.builder()
                       .id(orderId)
                       .menu(menu.toDto())
                       .build();
    }
}

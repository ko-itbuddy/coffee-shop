package org.itbuddy.coffeeshop.order.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.itbuddy.coffeeshop.menu.application.MenuDto;
import org.itbuddy.coffeeshop.order.application.OrderDto;

@Getter
@AllArgsConstructor
@Schema(description = "주문 응답 객체")
public class PostOrderResponse {
    @Schema(description = "주문 정보")
    private final OrderDto order;
}

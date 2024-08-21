package org.itbuddy.coffeeshop.order.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Schema(description = "주문 요청 객체")
public class PostOrderRequest {

    @Schema(description = "사용자 식별자(아이디)")
    @Positive(message = "사용자 아이디는 양수입니다.")
    @NotNull(message = "사용자 아이디는 필수값입니다.")
    private final Long userId;

    @Schema(description = "주문 메뉴 식별자(아이디)")
    @Positive(message = "주문 메뉴 아이디는 양수입니다.")
    @NotNull(message = "주문 메뉴 아이디는 필수값입니다.")
    private final Long menuId;

}

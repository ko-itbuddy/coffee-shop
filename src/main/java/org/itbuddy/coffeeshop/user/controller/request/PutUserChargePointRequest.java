package org.itbuddy.coffeeshop.user.controller.request;

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
@Schema(description = "포인트 충전 요청 객체")
public class PutUserChargePointRequest {

    @Schema(description = "사용자 식별자(아이디)")
    @Positive(message = "사용자 아이디는 양수입니다.")
    @NotNull(message = "사용자 아이디는 필수값입니다.")
    private final Long id;

    @Schema(description = "충전할 포인트")
    @Positive(message = "충전 포인트는 양수입니다.")
    @NotNull(message = "충전 포인트는 필수값입니다.")
    private final Integer point;

}

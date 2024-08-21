package org.itbuddy.coffeeshop.user.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.itbuddy.coffeeshop.user.application.UserDto;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Schema(description = "포인트 충전 응답 객체")
public class PutUserChargePointResponse {
    @Schema(description = "사용자")
    private final UserDto user;
}

package org.itbuddy.coffeeshop.user.controller;


import io.swagger.v3.oas.annotations.Operation;
import org.itbuddy.coffeeshop.common.ApiResponse;
import org.itbuddy.coffeeshop.menu.controller.response.GetMenuResponse;
import org.itbuddy.coffeeshop.user.controller.request.PutUserChargePointRequest;
import org.springframework.web.bind.annotation.RestController;

@RestController

public interface UserControllerDoc {

    @Operation(summary = "사용자 포인트를 충전", description="사용자 식별자와 포인트를 받아 사용자의 포인트를 충전합니다.")
    ApiResponse<Object> userPoint(PutUserChargePointRequest putUserChargePointRequest);
}

package org.itbuddy.coffeeshop.order.controller;


import io.swagger.v3.oas.annotations.Operation;
import org.itbuddy.coffeeshop.common.ApiResponse;
import org.itbuddy.coffeeshop.order.controller.request.PostOrderRequest;
import org.itbuddy.coffeeshop.order.controller.response.GetPopularMenuResponse;
import org.itbuddy.coffeeshop.order.controller.response.PostOrderResponse;

public interface OrderControllerDoc {

    @Operation(summary = "메뉴를 주문합니다.", description="사용자 식별자와 메뉴 아이드를 받아 주문하고 사용자의 포인트를 차감합니다.")
    ApiResponse<PostOrderResponse> createOrder(PostOrderRequest request);

    @Operation(summary = "인기 메뉴목록을 조회합니다.", description="지난 7일간 인기 메뉴 3개의 목록을 조회 합니다.")
    ApiResponse<GetPopularMenuResponse> getPopularMenuIn7Days();
}

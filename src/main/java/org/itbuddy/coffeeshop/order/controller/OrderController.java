package org.itbuddy.coffeeshop.order.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.itbuddy.coffeeshop.common.ApiResponse;
import org.itbuddy.coffeeshop.order.application.OrderService;
import org.itbuddy.coffeeshop.order.controller.request.PostOrderRequest;
import org.itbuddy.coffeeshop.order.controller.response.PostOrderResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController implements OrderControllerDoc {

    public static final String API_POST_ORDER = "/api/order";

    private final OrderService orderService;

    @PostMapping(API_POST_ORDER)
    public ApiResponse<PostOrderResponse> order(@Valid @RequestBody PostOrderRequest request) {
        return ApiResponse.ok(
            new PostOrderResponse(orderService.order(request.getUserId(), request.getMenuId()))
        );
    }

}

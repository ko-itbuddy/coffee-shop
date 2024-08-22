package org.itbuddy.coffeeshop.order.controller;


import jakarta.validation.Valid;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.itbuddy.coffeeshop.common.ApiResponse;
import org.itbuddy.coffeeshop.order.application.OrderService;
import org.itbuddy.coffeeshop.order.application.PopularMenuService;
import org.itbuddy.coffeeshop.order.controller.request.PostOrderRequest;
import org.itbuddy.coffeeshop.order.controller.response.GetPopularMenuResponse;
import org.itbuddy.coffeeshop.order.controller.response.PostOrderResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController implements OrderControllerDoc {

    public static final String API_GET_POPULAR_MENUS = "/api/popular-menus";
    public static final String API_POST_ORDER = "/api/order";

    private final OrderService orderService;
    private final PopularMenuService popularMenuService;

    @PostMapping(API_POST_ORDER)
    public ApiResponse<PostOrderResponse> order(@Valid @RequestBody PostOrderRequest request) {
        return ApiResponse.ok(
            new PostOrderResponse(orderService.order(request.getUserId(), request.getMenuId()))
        );
    }


    @Override
    @GetMapping(API_GET_POPULAR_MENUS)
    public ApiResponse<GetPopularMenuResponse> getPopularMenuIn7Days() {
        return ApiResponse.ok(
            new GetPopularMenuResponse(popularMenuService.getPopularMenuIn7Days(LocalDate.now()))
        );
    }

}

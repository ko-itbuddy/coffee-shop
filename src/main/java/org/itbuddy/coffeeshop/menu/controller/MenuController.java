package org.itbuddy.coffeeshop.menu.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.itbuddy.coffeeshop.common.ApiResponse;
import org.itbuddy.coffeeshop.menu.application.MenuService;
import org.itbuddy.coffeeshop.menu.controller.response.GetMenuResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MenuController implements MenuControllerDocs {

    private final MenuService menuService;

    @Override
    @Operation(summary = "모든 메뉴를 조회한다.")
    @GetMapping("api/menus")
    public ApiResponse<GetMenuResponse> getMenus() {
        return ApiResponse.ok(new GetMenuResponse(menuService.getMenus()));
    }
}

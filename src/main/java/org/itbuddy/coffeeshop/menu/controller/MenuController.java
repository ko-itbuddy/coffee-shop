package org.itbuddy.coffeeshop.menu.controller;

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

    public static final String API_GET_MENUS = "/api/menus";
    @Override
    @GetMapping(API_GET_MENUS)
    public ApiResponse<GetMenuResponse> getMenus() {
        return ApiResponse.ok(new GetMenuResponse(menuService.getMenus()));
    }
}

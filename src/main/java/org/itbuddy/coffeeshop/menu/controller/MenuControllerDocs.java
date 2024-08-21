package org.itbuddy.coffeeshop.menu.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.itbuddy.coffeeshop.common.ApiResponse;
import org.itbuddy.coffeeshop.menu.controller.response.GetMenuResponse;

public interface MenuControllerDocs {

    @Operation(summary = "메뉴 목록 조회", description = "모든 메뉴의 목록을 조회합니다.")
    ApiResponse<GetMenuResponse> getMenus();

}

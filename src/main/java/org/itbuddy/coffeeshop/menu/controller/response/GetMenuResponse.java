package org.itbuddy.coffeeshop.menu.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.itbuddy.coffeeshop.menu.application.MenuDto;

@Getter
@AllArgsConstructor
@Schema(description = "메뉴 조회 응답 객체")
public class GetMenuResponse {
    @Schema(description = "메뉴 리스트")
    private final List<MenuDto> menus;
}

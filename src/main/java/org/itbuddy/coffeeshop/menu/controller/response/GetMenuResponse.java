package org.itbuddy.coffeeshop.menu.controller.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.itbuddy.coffeeshop.menu.application.MenuDto;

@Getter
@AllArgsConstructor
public class GetMenuResponse {

    private final List<MenuDto> menus;
}

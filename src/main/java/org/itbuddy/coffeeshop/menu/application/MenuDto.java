package org.itbuddy.coffeeshop.menu.application;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "메뉴")
public class MenuDto {

    @Schema(description = "메뉴 ID")
    private final Long id;
    @Schema(description = "메뉴 명", example = "아메리카노")
    private final String name;
    @Schema(description = "메뉴 가격")
    private final int price;

    @Builder
    private MenuDto(Long id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}

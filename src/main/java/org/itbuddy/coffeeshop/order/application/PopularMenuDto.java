package org.itbuddy.coffeeshop.order.application;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.itbuddy.coffeeshop.menu.application.MenuDto;
import org.itbuddy.coffeeshop.menu.domain.MenuEntity;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "주문")
public class PopularMenuDto implements Serializable {

    @Schema(description = "메뉴 ID")
    private Long id;
    @Schema(description = "메뉴 명", example = "아메리카노")
    private String name;
    @Schema(description = "주문 수")
    private Long orderCnt;

    @Builder
    public PopularMenuDto(Long id, String name, Long orderCnt) {
        this.id = id;
        this.name = name;
        this.orderCnt = orderCnt;
    }
}

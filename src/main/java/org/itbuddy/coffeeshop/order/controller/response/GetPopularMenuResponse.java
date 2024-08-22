package org.itbuddy.coffeeshop.order.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;
import org.itbuddy.coffeeshop.order.application.PopularMenuDto;

@Getter
@Schema(description = "인기 메뉴 응답객체")
public class GetPopularMenuResponse {
    @Schema(description = "인기 메뉴 리스트")
    private final List<PopularMenuDto> popularMenus;

    public GetPopularMenuResponse(List<PopularMenuDto> popularMenus){
        this.popularMenus = popularMenus;
    }
}

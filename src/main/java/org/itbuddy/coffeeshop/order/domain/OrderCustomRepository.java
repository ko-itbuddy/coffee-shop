package org.itbuddy.coffeeshop.order.domain;

import java.time.LocalDate;
import java.util.List;
import org.itbuddy.coffeeshop.order.application.PopularMenuDto;

public interface OrderCustomRepository {

    List<PopularMenuDto> findPopularMenuIn7Days(LocalDate now);
}

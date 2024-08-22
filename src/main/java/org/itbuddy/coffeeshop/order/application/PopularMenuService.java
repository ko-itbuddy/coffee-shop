package org.itbuddy.coffeeshop.order.application;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.itbuddy.coffeeshop.order.domain.OrderCustomRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PopularMenuService {

    private final OrderCustomRepository orderCustomRepository;

    public List<PopularMenuDto> getPopularMenuIn7Days(LocalDate now){
        return orderCustomRepository.findPopularMenuIn7Days(now);
    }
}

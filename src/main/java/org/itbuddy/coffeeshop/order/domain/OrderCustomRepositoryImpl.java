package org.itbuddy.coffeeshop.order.domain;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.itbuddy.coffeeshop.menu.domain.QMenuEntity;
import org.itbuddy.coffeeshop.order.application.PopularMenuDto;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@RequiredArgsConstructor
public class OrderCustomRepositoryImpl implements OrderCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QOrderEntity order = new QOrderEntity("o1");

    @Override
    public List<PopularMenuDto> findPopularMenuIn7Days(LocalDate now) {
        return jpaQueryFactory.select(Projections.bean(PopularMenuDto.class, order.menuId.as("id"), order.menuName.as("name"), order.id.count().as("orderCnt")))
                              .where(
                                  new BooleanBuilder()
                                      .and(order.createdAt.goe(
                                          now.minusDays(8)
                                             .atStartOfDay()))
                                      .and(order.createdAt.lt(
                                          now.atStartOfDay()))
                              )
                              .from(order)
                              .groupBy(order.menuId, order.menuName)
                              .orderBy(order.menuId.count().desc())
                              .limit(3)
                              .fetch();
    }
}

package org.itbuddy.coffeeshop.order.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.itbuddy.coffeeshop.common.BaseEntity;
import org.itbuddy.coffeeshop.menu.domain.MenuEntity;
import org.itbuddy.coffeeshop.order.application.OrderDto;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
@Table(name = "TB_ORDER", indexes = {
    @Index(name = "idx__cratedAt__menuId__menuName", columnList = "createdAt, menuId, menuName")
})
public class OrderEntity extends BaseEntity implements Comparable<OrderEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long userId;

    @Column
    private Long menuId;

    @Column
    private String menuName;

    @Builder
    private OrderEntity(Long userId, Long menuId, String menuName) {
        this.userId = userId;
        this.menuId = menuId;
        this.menuName = menuName;
    }

    public static OrderEntity ofEntityByOrder(Long userId, MenuEntity menu){
        return OrderEntity.builder()
                          .userId(userId)
                          .menuId(menu.getId())
                          .menuName(menu.getName())
                          .build();
    }

    @Override
    public int compareTo(OrderEntity o) {
        return 1;
    }

    public OrderDto toDto(MenuEntity menu) {
        return OrderDto.builder()
                       .id(this.id)
                       .menu(menu.toDto())
                       .build();
    }

}

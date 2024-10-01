package org.itbuddy.coffeeshop.menu.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.itbuddy.coffeeshop.common.BaseEntity;
import org.itbuddy.coffeeshop.menu.application.MenuDto;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
@Table(name = "TB_MENU")
public class MenuEntity extends BaseEntity implements Comparable<MenuEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;


    @Column
    private Integer price;

    @Builder
    private MenuEntity(Long id, String name, Integer price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @Override
    public int compareTo(MenuEntity o) {
        return 1;
    }

}

package org.itbuddy.coffeeshop.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.SortedSet;
import java.util.TreeSet;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.itbuddy.coffeeshop.common.BaseEntity;
import org.itbuddy.coffeeshop.user.application.UserDto;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
@Table(name = "TB_USER")
public class UserEntity extends BaseEntity implements Comparable<UserEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    private String name;

    @Column
    @ColumnDefault("0")
    private Integer point = 0;

    @ToString.Exclude
    @OneToMany(mappedBy = "user")
    private SortedSet<UserPointTransactionEntity> userPointTransactions = new TreeSet<>();

    @Builder
    private UserEntity(Long id, String name, Integer point) {
        this.id = id;
        this.name = name;
        this.point = point == null ? 0 : point;
    }


    @Override
    public int compareTo(UserEntity o) {
        return 1;
    }

    public void chargePoint(Integer point) {
        this.point += point;
    }

    public void usePoint(Integer point) {
        if(this.point - point < 0) throw new IllegalArgumentException("포인트가 부족합니다");
        this.point -= point;
    }



    public UserDto toDto() {
        return UserDto.builder()
            .id(this.getId())
            .name(this.getName())
            .point(this.getPoint())
                         .build();
    }
}

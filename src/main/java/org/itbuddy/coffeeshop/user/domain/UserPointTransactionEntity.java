package org.itbuddy.coffeeshop.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.itbuddy.coffeeshop.common.BaseEntity;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
@Table(name = "TB_USER_POINT_TRANSACTION")
public class UserPointTransactionEntity extends BaseEntity implements
    Comparable<UserPointTransactionEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserEntity user;

    @Column
    @NotNull
    private Integer point;


    @Builder
    public UserPointTransactionEntity(UserEntity user, Integer point) {
        this.user = user;
        this.point = point;
    }

    @Override
    public int compareTo(UserPointTransactionEntity o) {
        return 1;
    }

    public static UserPointTransactionEntity createByCharge(UserEntity user, Integer point) {
        return new UserPointTransactionEntity(user, point);
    }
}

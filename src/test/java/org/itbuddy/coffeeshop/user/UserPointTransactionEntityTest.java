package org.itbuddy.coffeeshop.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.itbuddy.coffeeshop.user.domain.UserEntity;
import org.itbuddy.coffeeshop.user.domain.UserPointTransactionEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


class UserPointTransactionEntityTest {

    @Nested
    @DisplayName("chargePoint")
    class CreateByCharge {

        @Test
        @DisplayName("정상")
        void success() throws Exception {
            // given
            UserEntity user = createUserEntity( 1L,  "홍길동",  1000);
            // when
            UserPointTransactionEntity userPointTransaction = UserPointTransactionEntity.createByCharge(user, 1000);
            // then
            assertThat(userPointTransaction)
                .extracting( "user.id","user.name", "point")
                .containsExactly(1L,"홍길동", 1000);

        }
    }

    private UserEntity createUserEntity(Long id, String name, Integer point) {
        return UserEntity.builder()
                         .id(id)
                         .name(name)
                         .point(point)
                         .build();
    }

}

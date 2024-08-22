package org.itbuddy.coffeeshop.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.itbuddy.coffeeshop.user.domain.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


class UserEntityTest {

    @Nested
    @DisplayName("chargePoint")
    class ChargePoint {

        @Test
        @DisplayName("정상")
        void success() throws Exception {
            // given
            UserEntity user = createUserEntity( 1L,  "홍길동",  1000);
            // when
            user.chargePoint(1000);
            // then
            assertThat(user)
                .extracting("id", "name", "point")
                .containsExactly(1L, "홍길동", 2000);

        }
    }

    @Nested
    @DisplayName("usePoint")
    class UsePoint {

        @Test
        @DisplayName("정상")
        void success() throws Exception {
            // given
            UserEntity user = createUserEntity( 1L,  "홍길동",  1000);
            // when
            user.usePoint(1000);
            // then
            assertThat(user)
                .extracting("id", "name", "point")
                .containsExactly(1L, "홍길동", 0);

        }

        @Test
        @DisplayName("현재 사용자 포인트보다 더큰 금액을 차감")
        void notEnoughPoint() throws Exception {
            // given
            UserEntity user = createUserEntity(1L, "홍길동", 1000);
            // when
            // then
            assertThatThrownBy(() -> user.usePoint(10000)).isInstanceOf(
                IllegalArgumentException.class);

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

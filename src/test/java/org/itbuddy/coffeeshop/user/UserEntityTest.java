package org.itbuddy.coffeeshop.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.List;
import org.itbuddy.coffeeshop.config.TestContainerConfig;
import org.itbuddy.coffeeshop.user.application.UserService;
import org.itbuddy.coffeeshop.user.domain.UserEntity;
import org.itbuddy.coffeeshop.user.domain.UserPointTransactionRepository;
import org.itbuddy.coffeeshop.user.domain.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;


public class UserEntityTest {

    @Nested
    @DisplayName("chargePoint")
    class chargePoint {

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
    class  usePoint {

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
    }


    private UserEntity createUserEntity(Long id, String name, Integer point) {
        return UserEntity.builder()
                         .id(id)
                         .name(name)
                         .point(point)
                         .build();
    }

}

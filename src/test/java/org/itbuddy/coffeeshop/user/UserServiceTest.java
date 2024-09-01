package org.itbuddy.coffeeshop.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.itbuddy.coffeeshop.config.TestContainerConfig;
import org.itbuddy.coffeeshop.user.application.UserDto;
import org.itbuddy.coffeeshop.user.application.UserService;
import org.itbuddy.coffeeshop.user.domain.UserEntity;
import org.itbuddy.coffeeshop.user.domain.UserPointTransactionCustomRepository;
import org.itbuddy.coffeeshop.user.domain.UserPointTransactionEntity;
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

@ActiveProfiles("test")
@SpringBootTest
@ExtendWith(TestContainerConfig.class)
@ContextConfiguration(initializers = TestContainerConfig.IntegrationTestInitializer.class)
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserPointTransactionCustomRepository userPointTransactionCustomRepository;

    @Autowired
    UserPointTransactionRepository userPointTransactionRepository;


    @BeforeEach
    void init() throws Exception {

        UserEntity user1 = createUserEntity("울버린", 1000);
        UserEntity user2 = createUserEntity("푸우", 1000);
        UserEntity user3 = createUserEntity("마리오", 1000);
        UserEntity user4 = createUserEntity("기영이", 1000);

        userRepository.saveAll(List.of(user1, user2, user3, user4));
        userPointTransactionRepository.saveAll(List.of(
            UserPointTransactionEntity.createByCharge(user1, 1000),
            UserPointTransactionEntity.createByCharge(user2, 1000),
            UserPointTransactionEntity.createByCharge(user3, 1000),
            UserPointTransactionEntity.createByCharge(user4, 1000)
        ));
    }

    @AfterEach
    void tearDown() throws Exception {
        userRepository.deleteAllInBatch();
        userPointTransactionRepository.deleteAllInBatch();
    }

    @Nested
    @DisplayName("2. 사용자 포인트 충전")
    class GetMenus {

        @Test
        @DisplayName("정상")
        void success() throws Exception {
            // given
            UserEntity userEntity = userRepository.findAll().get(0);
            // when
            UserDto user = userService.chargePoint(userEntity.getId(), 10000);
            // then
            assertThat(user)
                .extracting("id", "name", "point")
                .containsExactly(
                    userEntity.getId(), "울버린", 11000
                );
            assertThat(userPointTransactionCustomRepository.findByUserId(user.getId()))
                .extracting("user.id", "point")
                .containsExactly(
                    tuple(userEntity.getId(), 1000),
                    tuple(userEntity.getId(), 10000)
                );
            Integer chargedUserPoint = userPointTransactionRepository.findAllByUserId(user.getId())
                                                                   .stream()
                                                                   .mapToInt(UserPointTransactionEntity::getPoint)
                                                                   .sum();
            assertThat(user.getPoint()).isEqualTo(chargedUserPoint);

        }

        @Test
        @DisplayName("3명의 유저가 동시에 3번 충전")
        void with10UserCharge10times() throws Exception {
            // given
            List<UserEntity> userEntities = userRepository.findAll();
            UserEntity user1 = userEntities.get(0);
            UserEntity user2 = userEntities.get(1);
            UserEntity user3 = userEntities.get(2);

            // when
            CountDownLatch latch = new CountDownLatch(3 * 3);
            ExecutorService executorService = Executors.newFixedThreadPool(6);

            makeCountDownLatch(latch, executorService, userEntities.get(0), 1000,
                latch.getCount() / 3);
            makeCountDownLatch(latch, executorService, userEntities.get(1), 1000,
                latch.getCount() / 3);
            makeCountDownLatch(latch, executorService, userEntities.get(2), 1000,
                latch.getCount() / 3);

            latch.await();

            UserEntity chargedUser1 = userRepository.findById(user1.getId())
                                                    .orElseThrow(() -> new IllegalArgumentException());
            // then
            assertThat(chargedUser1)
                .extracting("id", "name", "point")
                .containsExactly(user1.getId(), user1.getName(), 4000);
            assertThat(userPointTransactionCustomRepository.findByUserId(user1.getId()))
                .extracting("user.id", "point")
                .containsExactly(
                    tuple(user1.getId(), 1000),
                    tuple(user1.getId(), 1000),
                    tuple(user1.getId(), 1000),
                    tuple(user1.getId(), 1000)
                );
            Integer chargedUserPoint1 = userPointTransactionRepository.findAllByUserId(user1.getId())
                                                                   .stream()
                                                                   .mapToInt(UserPointTransactionEntity::getPoint)
                                                                   .sum();
            assertThat(chargedUser1.getPoint()).isEqualTo(chargedUserPoint1);

            UserEntity chargedUser2 = userRepository.findById(user2.getId())
                                                    .orElseThrow(() -> new IllegalArgumentException());

            assertThat(chargedUser2)
                .extracting("id", "name", "point")
                .containsExactly(user2.getId(), user2.getName(), 4000);
            assertThat(userPointTransactionCustomRepository.findByUserId(user2.getId()))
                .extracting("user.id", "point")
                .containsExactly(
                    tuple(user2.getId(), 1000),
                    tuple(user2.getId(), 1000),
                    tuple(user2.getId(), 1000),
                    tuple(user2.getId(), 1000)

                );

            Integer chargedUserPoint2 = userPointTransactionRepository.findAllByUserId(user2.getId())
                                                                   .stream()
                                                                   .mapToInt(UserPointTransactionEntity::getPoint)
                                                                   .sum();
            assertThat(chargedUser2.getPoint()).isEqualTo(chargedUserPoint2);

            UserEntity chargedUser3 = userRepository.findById(user3.getId())
                                                    .orElseThrow(() -> new IllegalArgumentException());

            assertThat(chargedUser3)
                .extracting("id", "name", "point")
                .containsExactly(user3.getId(), user3.getName(), 4000);
            assertThat(userPointTransactionCustomRepository.findByUserId(user3.getId()))
                .extracting("user.id", "point")
                .containsExactly(
                    tuple(user3.getId(), 1000),
                    tuple(user3.getId(), 1000),
                    tuple(user3.getId(), 1000),
                    tuple(user3.getId(), 1000)
                );

            Integer chargedUserPoint3 = userPointTransactionRepository.findAllByUserId(user3.getId())
                                                                   .stream()
                                                                   .mapToInt(UserPointTransactionEntity::getPoint)
                                                                   .sum();
            assertThat(chargedUser3.getPoint()).isEqualTo(chargedUserPoint3);

        }
    }


    private void makeCountDownLatch(CountDownLatch latch, ExecutorService executorService,
        UserEntity user, Integer point, long repeat) {

        for (int i = 0; i < repeat; i++) {
            int finalI = i;
            executorService.submit(() -> {
                try {
                    userService.chargePoint(user.getId(), point);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    latch.countDown();
                }
            });
        }
    }

    private UserEntity createUserEntity(String name, Integer point) {
        return UserEntity.builder()
                         .name(name)
                         .point(point)
                         .build();
    }

}

package org.itbuddy.coffeeshop.user.application;

import lombok.RequiredArgsConstructor;
import org.itbuddy.coffeeshop.config.distributionlock.DistributedLock;
import org.itbuddy.coffeeshop.user.domain.UserEntity;
import org.itbuddy.coffeeshop.user.domain.UserPointTransactionEntity;
import org.itbuddy.coffeeshop.user.domain.UserPointTransactionRepository;
import org.itbuddy.coffeeshop.user.domain.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserPointTransactionRepository userPointTransactionRepository;

    @Transactional
    @DistributedLock("#userId")
    public UserDto chargePoint(Long userId, Integer point) throws Exception{
        final UserEntity user = userRepository.findById(userId).orElseThrow(
            () -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        user.chargePoint(point);
        userRepository.save(user);
        userPointTransactionRepository.save(UserPointTransactionEntity.createByCharge(user,point));
        return UserDto.of(user);
    }
}

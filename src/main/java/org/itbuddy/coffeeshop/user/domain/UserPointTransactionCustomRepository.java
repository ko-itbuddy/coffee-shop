package org.itbuddy.coffeeshop.user.domain;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPointTransactionCustomRepository {
    List<UserPointTransactionEntity> findByUserId(Long userId);
}

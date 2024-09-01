package org.itbuddy.coffeeshop.user.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPointTransactionRepository extends
    JpaRepository<UserPointTransactionEntity, Long> {
    List<UserPointTransactionEntity> findAllByUserId(Long userId);
}

package org.itbuddy.coffeeshop.user.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserPointTransactionCustomRepositoryImpl implements
    UserPointTransactionCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QUserPointTransactionEntity qUserPointTransactionEntity = new QUserPointTransactionEntity(
        "upt1");


    @Override
    public List<UserPointTransactionEntity> findByUserId(Long userId) {
        return jpaQueryFactory.select(qUserPointTransactionEntity)
                              .where(qUserPointTransactionEntity.user.id.eq(userId))
                              .from(qUserPointTransactionEntity).fetch();
    }
}

package com.walker.transaction.rmb.repository;

import com.walker.transaction.rmb.RmbAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author fcwalker
 * @date 2020/12/19 10:36
 **/
@Repository
public interface RmbAccountRepository extends JpaRepository<RmbAccount, Integer> {
    /**
     * 支付
     * @param payerId
     * @param count
     * @return
     */
    @Modifying
    @Query("update RmbAccount set amount = amount - :count where userId = :payerId")
    int payment(@Param("payerId") Integer payerId, @Param("count") Integer count);

    /**
     * 收款
     * @param payerId
     * @param count
     * @return
     */
    @Modifying
    @Query("update RmbAccount set amount = amount + :count where userId = :payerId")
    int collection(@Param("payerId") Integer payerId, @Param("count") Integer count);

    /**
     * 支付取消
     * @param payerId
     * @param count
     * @return
     */
    @Modifying
    @Query("update RmbAccount set amount = amount + :count where userId = :payerId")
    int paymentCancel(@Param("payerId") Integer payerId, @Param("count") Integer count);

}

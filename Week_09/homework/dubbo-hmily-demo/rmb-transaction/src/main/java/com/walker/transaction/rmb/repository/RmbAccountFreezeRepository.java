package com.walker.transaction.rmb.repository;

import com.walker.transaction.rmb.RmbAccountFreeze;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author fcwalker
 * @date 2020/12/19 10:38
 **/
@Repository
public interface RmbAccountFreezeRepository extends JpaRepository<RmbAccountFreeze, Integer> {

    RmbAccountFreeze findByUserIdAndFreezeType(Integer userId, Integer freezeType);
    /**
     * 款项冻结
     * @param payerId
     * @param count
     * @param freezeType
     * @return
     */
    @Modifying
    @Query("update RmbAccountFreeze set amount = amount + :count where userId = :payerId and freezeType = :freezeType")
    int accountFreeze(@Param("payerId") Integer payerId, @Param("count") Integer count,  @Param("freezeType") Integer freezeType);

    /**
     * 释放冻结的款项
     * @param payerId
     * @param count
     * @param freezeType
     * @return
     */
    @Modifying
    @Query("update RmbAccountFreeze set amount = amount - :count where userId = :payerId and freezeType = :freezeType")
    int accountFinish(@Param("payerId") Integer payerId, @Param("count") Integer count,  @Param("freezeType") Integer freezeType);

}

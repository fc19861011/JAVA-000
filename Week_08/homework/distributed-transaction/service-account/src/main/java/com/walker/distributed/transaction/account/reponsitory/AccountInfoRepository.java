package com.walker.distributed.transaction.account.reponsitory;

import com.walker.distributed.transaction.account.domain.AccountInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author dell
 * @date 2020/12/2 18:34
 **/
@Repository
public interface AccountInfoRepository extends JpaRepository<AccountInfoEntity, Long> {
    AccountInfoEntity findAccountByUserId(String userid);
}

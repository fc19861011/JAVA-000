package com.walker.insert.repository;

import com.walker.insert.domain.OrderInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author dell
 * @date 2020/12/2 18:34
 **/
@Repository
public interface OrderInfoRepository extends JpaRepository<OrderInfoEntity, Long> {}

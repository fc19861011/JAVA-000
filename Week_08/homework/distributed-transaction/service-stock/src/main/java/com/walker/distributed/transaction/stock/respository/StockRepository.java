package com.walker.distributed.transaction.stock.respository;

import com.walker.distributed.transaction.stock.domain.StockEntitiy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<StockEntitiy, Integer> {

    StockEntitiy getByProductId(String productId);
}

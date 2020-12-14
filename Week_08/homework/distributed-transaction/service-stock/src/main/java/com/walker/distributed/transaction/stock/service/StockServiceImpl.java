package com.walker.distributed.transaction.stock.service;

import com.walker.distributed.transaction.stock.domain.StockEntitiy;
import com.walker.distributed.transaction.stock.respository.StockRepository;
import com.walker.tx.common.dto.InventoryDTO;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StockServiceImpl implements StockService {

    @Autowired
    StockRepository stockRepository;

    @Override
    @HmilyTCC(confirmMethod = "confirm", cancelMethod = "cancel")
    public boolean decrease(InventoryDTO inventoryDTO) {
        StockEntitiy stockEntitiy = stockRepository.getByProductId(inventoryDTO.getProductId());
        stockEntitiy.setLockInventory(stockEntitiy.getLockInventory() + inventoryDTO.getCount());
        stockEntitiy.setTotalInventory(stockEntitiy.getTotalInventory() - inventoryDTO.getCount());
        stockRepository.save(stockEntitiy);
        return true;
    }

    public boolean confirm(final InventoryDTO inventoryDTO) {
        log.info("============执行confirm 减库存接口===============");
        StockEntitiy stockEntitiy = stockRepository.getByProductId(inventoryDTO.getProductId());
        stockEntitiy.setLockInventory(stockEntitiy.getLockInventory() - inventoryDTO.getCount());
        stockRepository.save(stockEntitiy);
        return true;
    }

    public boolean cancel(final InventoryDTO inventoryDTO) {
        log.info("============cancel 减库存接口===============");
        StockEntitiy stockEntitiy = stockRepository.getByProductId(inventoryDTO.getProductId());
        stockEntitiy.setLockInventory(stockEntitiy.getLockInventory() - inventoryDTO.getCount());
        stockEntitiy.setTotalInventory(stockEntitiy.getTotalInventory() + inventoryDTO.getCount());
        stockRepository.save(stockEntitiy);
        return true;
    }
}

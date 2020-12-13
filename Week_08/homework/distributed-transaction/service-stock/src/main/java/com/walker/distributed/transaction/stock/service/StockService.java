package com.walker.distributed.transaction.stock.service;

import com.walker.tx.common.dto.InventoryDTO;

public interface StockService {
    boolean decrease(InventoryDTO inventoryDTO);
}

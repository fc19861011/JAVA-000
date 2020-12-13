package com.walker.distributed.transaction.order.client;

import com.sun.javafx.binding.StringFormatter;
import com.walker.tx.common.dto.InventoryDTO;
import org.springframework.stereotype.Component;

@Component
public class StockHystrix implements StockClient {
    @Override
    public Boolean decrease(InventoryDTO inventoryDTO) {
        System.out.println(StringFormatter.format("执行断路器，库存参数：%s", inventoryDTO.toString()));
        return false;
    }
}

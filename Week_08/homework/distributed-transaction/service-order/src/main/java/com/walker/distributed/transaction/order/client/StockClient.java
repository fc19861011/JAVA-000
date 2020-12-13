package com.walker.distributed.transaction.order.client;

import com.walker.tx.common.dto.InventoryDTO;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "stock-service")
public interface StockClient {
    @RequestMapping("/stock-service/stock/decrease")
    @Hmily
    Boolean decrease(@RequestBody InventoryDTO inventoryDTO);
}

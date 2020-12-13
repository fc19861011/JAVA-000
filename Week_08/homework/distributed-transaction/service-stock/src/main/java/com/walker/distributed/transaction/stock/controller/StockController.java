package com.walker.distributed.transaction.stock.controller;

import com.walker.distributed.transaction.stock.service.StockService;
import com.walker.tx.common.dto.InventoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/stock")
public class StockController {

    @Autowired
    StockService stockService;

    @PostMapping("/decrease")
    public boolean decrease(InventoryDTO inventoryDTO) {
        return stockService.decrease(inventoryDTO);
    }
}

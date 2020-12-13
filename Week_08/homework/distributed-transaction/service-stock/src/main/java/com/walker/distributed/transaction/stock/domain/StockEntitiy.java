package com.walker.distributed.transaction.stock.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "inventory")
@Data
public class StockEntitiy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商品id.
     */
    private String productId;

    /**
     * 总库存.
     */
    private Integer totalInventory;

    /**
     * 锁定库存.
     */
    private Integer lockInventory;
}

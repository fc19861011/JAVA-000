package com.walker.sharding.jdbc.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author fc walker
 * @date 2020/12/2 18:00
 **/
@Entity(name = "order_info")
@Data
public class OrderInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private String orderCode;
    private byte orderStatus;
    private Long createTime;
    private Long paymentTime;
    private Long deliveryTime;
    private Long expectedTime;
    private Long arriveTime;
    private Long completeTime;
    private Long merchantId;
    private String merchantName;
    private Long customerId;
    private String customerName;
}

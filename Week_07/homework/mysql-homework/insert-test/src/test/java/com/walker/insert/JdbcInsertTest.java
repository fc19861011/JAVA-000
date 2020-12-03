package com.walker.insert;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.lang.Console;
import com.walker.insert.domain.OrderInfoEntity;
import com.walker.insert.service.OrderInfoJdbcService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InsertTest.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JdbcInsertTest {
    @Autowired
    OrderInfoJdbcService orderInfoJdbcService;
    private final int totalNum = 1000000;

    @Test
    public void TestSingeInsert() {
        TimeInterval timer = DateUtil.timer();
        for(int i = 0; i < totalNum; i++) {
            OrderInfoEntity orderInfoEntity = new OrderInfoEntity();
            orderInfoEntity.setOrderCode("123123123"+i);
            orderInfoEntity.setOrderStatus((byte) 1);
            orderInfoEntity.setCreateTime(DateTime.now().getTime());
            orderInfoEntity.setPaymentTime(DateTime.now().getTime());
            orderInfoEntity.setDeliveryTime(DateTime.now().getTime());
            orderInfoEntity.setExpectedTime(DateTime.now().getTime());
            orderInfoEntity.setArriveTime(DateTime.now().getTime());
            orderInfoEntity.setCompleteTime(DateTime.now().getTime());
            orderInfoEntity.setMerchantId(Long.valueOf(i));
            orderInfoEntity.setMerchantName("merchantName"+i);
            orderInfoEntity.setCustomerId(Long.valueOf(i));
            orderInfoEntity.setCustomerName("customerName"+i);
            orderInfoJdbcService.insert(orderInfoEntity);
        }
        Console.log("花费时间：", timer.interval());
    }

    @Test
    public void TestBatchInsert() {
        TimeInterval timer = DateUtil.timer();
        List<OrderInfoEntity> orderInfoEntities = new ArrayList<>();
        for(int i = 0; i < totalNum; i++) {
            OrderInfoEntity orderInfoEntity = new OrderInfoEntity();
            orderInfoEntity.setOrderCode("123123123"+i);
            orderInfoEntity.setOrderStatus((byte) 1);
            orderInfoEntity.setCreateTime(DateTime.now().getTime());
            orderInfoEntity.setPaymentTime(DateTime.now().getTime());
            orderInfoEntity.setDeliveryTime(DateTime.now().getTime());
            orderInfoEntity.setExpectedTime(DateTime.now().getTime());
            orderInfoEntity.setArriveTime(DateTime.now().getTime());
            orderInfoEntity.setCompleteTime(DateTime.now().getTime());
            orderInfoEntity.setMerchantId(Long.valueOf(i));
            orderInfoEntity.setMerchantName("merchantName"+i);
            orderInfoEntity.setCustomerId(Long.valueOf(i));
            orderInfoEntity.setCustomerName("customerName"+i);
            orderInfoEntities.add(orderInfoEntity);
        }
        orderInfoJdbcService.insertBatch(orderInfoEntities);
        Console.log("花费时间：", timer.interval());
    }

    final int batch_size = 10000;

    @Test
    public void TestValuesInsert() {
        TimeInterval timer = DateUtil.timer();
        List<OrderInfoEntity> orderInfoEntities = new ArrayList<>();
        for(int i = 0; i < totalNum; i++) {
            OrderInfoEntity orderInfoEntity = new OrderInfoEntity();
            orderInfoEntity.setOrderCode("123123123"+i);
            orderInfoEntity.setOrderStatus((byte) 1);
            orderInfoEntity.setCreateTime(DateTime.now().getTime());
            orderInfoEntity.setPaymentTime(DateTime.now().getTime());
            orderInfoEntity.setDeliveryTime(DateTime.now().getTime());
            orderInfoEntity.setExpectedTime(DateTime.now().getTime());
            orderInfoEntity.setArriveTime(DateTime.now().getTime());
            orderInfoEntity.setCompleteTime(DateTime.now().getTime());
            orderInfoEntity.setMerchantId(Long.valueOf(i));
            orderInfoEntity.setMerchantName("merchantName"+i);
            orderInfoEntity.setCustomerId(Long.valueOf(i));
            orderInfoEntity.setCustomerName("customerName"+i);
            orderInfoEntities.add(orderInfoEntity);
            if((i+1) % batch_size == 0) {
                orderInfoJdbcService.insertBatch(orderInfoEntities);
                orderInfoEntities.clear();
            }
        }
        if(orderInfoEntities.size() > 0) {
            orderInfoJdbcService.insertBatch(orderInfoEntities);
        }
        Console.log("花费时间：", timer.interval());
    }
}

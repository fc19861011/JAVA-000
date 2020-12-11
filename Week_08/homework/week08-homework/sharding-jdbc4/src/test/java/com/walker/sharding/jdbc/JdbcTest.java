package com.walker.sharding.jdbc;

import cn.hutool.core.date.DateTime;
import com.walker.sharding.jdbc.domain.OrderInfoEntity;
import com.walker.sharding.jdbc.repository.OrderInfoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShardingJdbcExampleApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JdbcTest {

    @Autowired
    OrderInfoRepository orderInfoRepository;

    /**
     * test save data by sharding proxy plugin
     */
    @Test
    public void save() {
        int order_count = 10;
        for (int i = 0; i < order_count; i++) {
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
            orderInfoRepository.save(orderInfoEntity);
        }
    }

}

package com.walker.insert.service;

import com.walker.insert.constant.OrderInfoSql;
import com.walker.insert.domain.OrderInfoEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dell
 * @date 2020/12/2 18:44
 **/
@Service
@Transactional(rollbackFor = Exception.class)
@AllArgsConstructor
public class OrderInfoJdbcServiceImpl implements OrderInfoJdbcService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void insert(OrderInfoEntity orderInfoEntity) {
        jdbcTemplate.update(OrderInfoSql.INSERT_STR,
                new Object[] {orderInfoEntity.getOrderCode(), orderInfoEntity.getOrderStatus(),
                        orderInfoEntity.getCreateTime(), orderInfoEntity.getPaymentTime(),
                        orderInfoEntity.getDeliveryTime(), orderInfoEntity.getExpectedTime(),
                        orderInfoEntity.getArriveTime(), orderInfoEntity.getCompleteTime(),
                        orderInfoEntity.getMerchantId(), orderInfoEntity.getMerchantName(),
                        orderInfoEntity.getCustomerId(), orderInfoEntity.getCustomerName()});
    }

    @Override
    public void insertBatch(List<OrderInfoEntity> orderInfoEntities) {
//        TimeInterval timer = DateUtil.timer();
        List<Object[]> values = new ArrayList<>(orderInfoEntities.size());
        for (OrderInfoEntity orderInfoEntity : orderInfoEntities) {
            values.add(
                    new Object[] {orderInfoEntity.getOrderCode(), orderInfoEntity.getOrderStatus(),
                            orderInfoEntity.getCreateTime(), orderInfoEntity.getPaymentTime(),
                            orderInfoEntity.getDeliveryTime(), orderInfoEntity.getExpectedTime(),
                            orderInfoEntity.getArriveTime(), orderInfoEntity.getCompleteTime(),
                            orderInfoEntity.getMerchantId(), orderInfoEntity.getMerchantName(),
                            orderInfoEntity.getCustomerId(), orderInfoEntity.getCustomerName()});
        }
        jdbcTemplate.batchUpdate(OrderInfoSql.INSERT_STR, values);
//        Console.log("批量提交{}数据，所花费时间：{}", orderInfoEntities.size(), timer.interval());
    }

}

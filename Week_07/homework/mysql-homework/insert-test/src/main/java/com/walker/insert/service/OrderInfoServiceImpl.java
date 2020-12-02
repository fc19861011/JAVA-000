package com.walker.insert.service;

import com.walker.insert.domain.OrderInfoEntity;
import com.walker.insert.repository.OrderInfoRepository;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * @author dell
 * @date 2020/12/2 18:44
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    EntityManager entityManager;

    @Autowired
    OrderInfoRepository orderInfoRepository;

    @Override
    public void insert(OrderInfoEntity orderInfoEntity) {
        orderInfoRepository.save(orderInfoEntity);
    }

    @Override
    public void insertBatch(List<OrderInfoEntity> orderInfoEntities) {
        orderInfoRepository.saveAll(orderInfoEntities);
    }

    @Override
    public void insertValues(List<OrderInfoEntity> orderInfoEntities) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO order_info (").append(
                "order_code, order_status, create_time, payment_time, delivery_time, expected_time, arrive_time, ")
                .append("complete_time, merchant_id, merchant_name, customer_id, customer_name ) ")
                .append("VALUES");
        for (OrderInfoEntity orderInfoEntity : orderInfoEntities) {
            stringBuilder.append("(").append(orderInfoEntity.getOrderCode()).append(", ")
                    .append(orderInfoEntity.getOrderStatus()).append(", ")
                    .append(orderInfoEntity.getCreateTime()).append(", ")
                    .append(orderInfoEntity.getPaymentTime()).append(", ")
                    .append(orderInfoEntity.getDeliveryTime()).append(", ")
                    .append(orderInfoEntity.getExpectedTime()).append(", ")
                    .append(orderInfoEntity.getArriveTime()).append(", ")
                    .append(orderInfoEntity.getCompleteTime()).append(", ")
                    .append(orderInfoEntity.getMerchantId()).append(", ")
                    .append(orderInfoEntity.getMerchantName()).append(", ")
                    .append(orderInfoEntity.getCustomerId()).append(", ")
                    .append(orderInfoEntity.getCustomerName()).append("),");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        NativeQuery query = (NativeQuery) entityManager.createNativeQuery(stringBuilder.toString());
        query.executeUpdate();
    }
}

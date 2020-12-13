package com.walker.distributed.transaction.order.service;

import com.walker.distributed.transaction.order.client.AccountClient;
import com.walker.distributed.transaction.order.domain.OrderInfoEntity;
import com.walker.distributed.transaction.order.repository.OrderInfoRepository;
import com.walker.tx.common.dto.AccountDTO;
import com.walker.tx.common.enums.OrderInfoStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    OrderInfoRepository repository;

    @Autowired
    AccountClient accountClient;

    @Override
    @HmilyTCC(confirmMethod = "confirmOrderStatus", cancelMethod = "cancelOrderStatus")
    public boolean makePayment(OrderInfoEntity orderInfoEntity) {
        updateOrderStatus(orderInfoEntity, OrderInfoStatusEnum.PAYING);
        accountClient.payment(buildAccountDTO(orderInfoEntity));
        return true;
    }


    private void updateOrderStatus(OrderInfoEntity order, OrderInfoStatusEnum orderStatus) {
        order.setStatus(orderStatus.getCode());
        repository.save(order);
    }

    private AccountDTO buildAccountDTO(OrderInfoEntity entity) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAmount(entity.getTotalAmount());
        accountDTO.setUserId(entity.getUserId());
        return accountDTO;
    }

    public void confirmOrderStatus(OrderInfoEntity order) {
        updateOrderStatus(order, OrderInfoStatusEnum.PAY_SUCCESS);
        log.info("=========进行订单confirm操作完成================");
    }

    public void cancelOrderStatus(OrderInfoEntity order) {
        updateOrderStatus(order, OrderInfoStatusEnum.PAY_FAIL);
        log.info("=========进行订单cancel操作完成================");
    }

}

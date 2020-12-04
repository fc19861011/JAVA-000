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
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

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
        for (int i = 0; i < totalNum; i++) {
            OrderInfoEntity orderInfoEntity = new OrderInfoEntity();
            orderInfoEntity.setOrderCode("123123123" + i);
            orderInfoEntity.setOrderStatus((byte) 1);
            orderInfoEntity.setCreateTime(DateTime.now().getTime());
            orderInfoEntity.setPaymentTime(DateTime.now().getTime());
            orderInfoEntity.setDeliveryTime(DateTime.now().getTime());
            orderInfoEntity.setExpectedTime(DateTime.now().getTime());
            orderInfoEntity.setArriveTime(DateTime.now().getTime());
            orderInfoEntity.setCompleteTime(DateTime.now().getTime());
            orderInfoEntity.setMerchantId(Long.valueOf(i));
            orderInfoEntity.setMerchantName("merchantName" + i);
            orderInfoEntity.setCustomerId(Long.valueOf(i));
            orderInfoEntity.setCustomerName("customerName" + i);
            orderInfoJdbcService.insert(orderInfoEntity);
        }
        Console.log("花费时间：", timer.interval());
    }

    final int batch_size = 8000;

    @Test
    public void TestBatchInsert() {
        TimeInterval timer = DateUtil.timer();
        List<OrderInfoEntity> orderInfoEntities = new ArrayList<>();
        for (int i = 0; i < totalNum; i++) {
            OrderInfoEntity orderInfoEntity = new OrderInfoEntity();
            orderInfoEntity.setOrderCode("123123123" + i);
            orderInfoEntity.setOrderStatus((byte) 1);
            orderInfoEntity.setCreateTime(DateTime.now().getTime());
            orderInfoEntity.setPaymentTime(DateTime.now().getTime());
            orderInfoEntity.setDeliveryTime(DateTime.now().getTime());
            orderInfoEntity.setExpectedTime(DateTime.now().getTime());
            orderInfoEntity.setArriveTime(DateTime.now().getTime());
            orderInfoEntity.setCompleteTime(DateTime.now().getTime());
            orderInfoEntity.setMerchantId(Long.valueOf(i));
            orderInfoEntity.setMerchantName("merchantName" + i);
            orderInfoEntity.setCustomerId(Long.valueOf(i));
            orderInfoEntity.setCustomerName("customerName" + i);
            orderInfoEntities.add(orderInfoEntity);
            if ((i + 1) % batch_size == 0) {
                orderInfoJdbcService.insertBatch(orderInfoEntities);
                orderInfoEntities.clear();
            }
        }
        if (orderInfoEntities.size() > 0) {
            orderInfoJdbcService.insertBatch(orderInfoEntities);
        }
        Console.log("花费时间：", timer.interval());
    }

    private ExecutorService saveProxy;
    @Autowired
    private TransactionTemplate transactionTemplate;


    @Test
    public void testThreadPool() throws InterruptedException {
        TimeInterval timer = DateUtil.timer();
        int cores = Runtime.getRuntime().availableProcessors() * 2;
        initPool(cores);
        int batch_num = totalNum / cores;
        List<OrderInfoEntity> orderInfoEntities = new ArrayList<>();
        for (int i = 0; i < totalNum; i++) {
            OrderInfoEntity orderInfoEntity = new OrderInfoEntity();
            orderInfoEntity.setOrderCode("123123123" + i);
            orderInfoEntity.setOrderStatus((byte) 1);
            orderInfoEntity.setCreateTime(DateTime.now().getTime());
            orderInfoEntity.setPaymentTime(DateTime.now().getTime());
            orderInfoEntity.setDeliveryTime(DateTime.now().getTime());
            orderInfoEntity.setExpectedTime(DateTime.now().getTime());
            orderInfoEntity.setArriveTime(DateTime.now().getTime());
            orderInfoEntity.setCompleteTime(DateTime.now().getTime());
            orderInfoEntity.setMerchantId(Long.valueOf(i));
            orderInfoEntity.setMerchantName("merchantName" + i);
            orderInfoEntity.setCustomerId(Long.valueOf(i));
            orderInfoEntity.setCustomerName("customerName" + i);
            orderInfoEntities.add(orderInfoEntity);
            if ((i + 1) % batch_size == 0) {
                List<OrderInfoEntity> orderInfoEntitiesBak = new ArrayList<>(orderInfoEntities);
                handle(orderInfoEntitiesBak);
                orderInfoEntities.clear();
            }
        }
        if (orderInfoEntities.size() > 0) {
            handle(orderInfoEntities);
        }
        this.saveProxy.shutdown();
        while (!this.saveProxy.isTerminated()) {
        }
        Console.log("花费时间：", timer.interval());
    }

    private void initPool(final int cores) {
        // IO密集型
        long keepAliveTime = 500;
        int queueSize = 2048;
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
        saveProxy = new ThreadPoolExecutor(cores, cores, keepAliveTime, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(queueSize), new NamedThreadFactory("proxyService"),
                handler);
    }

    public void handle(final List<OrderInfoEntity> orderInfoEntities) {
        saveProxy.submit(() -> saveBatch(orderInfoEntities));
    }

    private void saveBatch(List<OrderInfoEntity> orderInfoEntities) {
        orderInfoJdbcService.insertBatch(orderInfoEntities);
    }

    class NamedThreadFactory implements ThreadFactory {

        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);

        private final String namePrefix;
        private final boolean daemon;

        public NamedThreadFactory(String namePrefix, boolean daemon) {
            this.daemon = daemon;
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            this.namePrefix = namePrefix;
        }

        public NamedThreadFactory(String namePrefix) {
            this(namePrefix, false);
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + "-thread-" + threadNumber.getAndIncrement(), 0);
            t.setDaemon(daemon);
            return t;
        }
    }

}

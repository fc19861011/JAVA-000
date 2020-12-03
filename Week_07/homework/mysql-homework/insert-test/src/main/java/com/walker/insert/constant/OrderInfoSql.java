package com.walker.insert.constant;

/**
 * @author dell
 * @date 2020/12/3 13:48
 **/
public interface OrderInfoSql {
    String INSERT_START = "INSERT INTO ORDER_INFO ";
    String INSERT_COLUMNS =
            "(order_code, order_status, create_time, payment_time, delivery_time, expected_time, arrive_time, complete_time, merchant_id, merchant_name, customer_id, customer_name ) ";
    String INSERT_VALUES = "(?,?,?,?,?,?,?,?,?,?,?,?)";
    String INSERT_STR = INSERT_START + INSERT_COLUMNS + " VALUES " + INSERT_VALUES;

}

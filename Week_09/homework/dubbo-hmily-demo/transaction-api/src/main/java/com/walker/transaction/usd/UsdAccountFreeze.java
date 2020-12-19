package com.walker.transaction.usd;

import lombok.Data;

import javax.persistence.*;

/**
 * @author fcwalker
 * @date 2020/12/18 16:41
 **/
@Data
@Entity
@Table(name = "usd_account_freeze")
public class UsdAccountFreeze {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private Integer amount;
    private Integer freezeType;
}

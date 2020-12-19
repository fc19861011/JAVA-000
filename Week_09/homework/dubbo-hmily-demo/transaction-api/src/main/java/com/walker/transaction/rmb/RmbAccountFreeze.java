package com.walker.transaction.rmb;

import lombok.Data;

import javax.persistence.*;

/**
 * @author fcwalker
 * @date 2020/12/18 16:41
 **/
@Data
@Entity
@Table(name = "rmb_account_freeze")
public class RmbAccountFreeze {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private Integer amount;
    private Integer freezeType;
}

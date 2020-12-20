package com.walker.transaction.usd;

import lombok.Data;

import javax.persistence.*;

/**
 * @author fcwalker
 * @date 2020/12/18 16:41
 **/
@Data
@Entity
@Table(name = "freeze_usd_account")
public class UsdAccountFreeze {
    @Id
    private Integer userId;
    private Integer amount;
    private Integer freezeType;
}

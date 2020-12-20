package com.walker.transaction.rmb;

import lombok.Data;

import javax.persistence.*;

/**
 * @author fcwalker
 * @date 2020/12/18 16:41
 **/
@Data
@Entity
@Table(name = "freeze_rmb_account")
public class RmbAccountFreeze {
    @Id
    private Integer userId;
    private Integer amount;
    private Integer freezeType;
}

package com.walker.transaction.rmb;

import lombok.Data;
import org.springframework.context.annotation.Lazy;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author fcwalker
 * @date 2020/12/18 16:41
 **/
@Data
@Entity
@Table(name = "freeze_rmb_account")
@Lazy(false)
public class RmbAccountFreeze {
    @Id
    private Integer userId;
    private Integer amount;
    private Long freezeType;
}

package com.walker.transaction.usd;

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
@Table(name = "freeze_usd_account")
@Lazy(false)
public class UsdAccountFreeze {
    @Id
    private Integer userId;
    private Integer amount;
    private Integer freezeType;
}

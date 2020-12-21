package com.walker.transaction.rmb;

import lombok.Data;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;

/**
 * @author fcwalker
 * @date 2020/12/18 16:41
 **/
@Data
@Entity
@Table(name = "rmb_account")
@Lazy(false)
public class RmbAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private Integer amount;
}

package com.walker.rpc.common.entitiy;

import lombok.Data;

import javax.persistence.*;

/**
 * @author dell
 * @date 2020/12/16 16:52
 **/
@Entity
@Table(name = "user_info")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
}

package com.walker.rws.domain;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "test")
@Data
public class TestEnity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
}

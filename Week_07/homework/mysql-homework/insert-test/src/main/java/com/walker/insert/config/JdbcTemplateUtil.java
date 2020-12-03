package com.walker.insert.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author dell
 * @date 2020/12/3 13:37
 **/
public class JdbcTemplateUtil {
    @Autowired
    JdbcTemplate jdbcTemplate;
}

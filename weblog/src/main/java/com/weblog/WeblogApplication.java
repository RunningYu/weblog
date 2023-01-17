package com.weblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author 其然乐衣Letitbe
 */

/**
 * 注解开启事务（其实 Spring Boot 默认就是开启事务的），其次就是实现回滚的方法必须是 public 的
 */
@EnableTransactionManagement
@SpringBootApplication
public class WeblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeblogApplication.class, args);
    }

}

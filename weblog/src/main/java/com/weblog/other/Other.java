package com.weblog.other;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/13
 */
@Configuration
public class Other {
    @Value("${server.port}")
    private String port;

    public String printValue() {
        return port;
    }

}

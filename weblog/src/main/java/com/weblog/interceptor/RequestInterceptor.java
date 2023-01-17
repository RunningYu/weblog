package com.weblog.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/3
 */
@Configuration
public class RequestInterceptor implements WebMvcConfigurer {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 不用过滤的url
     */
    public List<String> patterns = new ArrayList<String>();

    public void addInterceptor(InterceptorRegistry registry) {

        // 写一个拦截器
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

                // 从请求头中获取token
                String token = request.getHeader("token");
                // 从 redis 中获取 token
                if (token != null && redisTemplate.opsForValue().get(token) != null) {
                    // 重新设置 token 这个 键 的存活时间
                    // 每次认证后就充值为30天                    时间单位：天
                    redisTemplate.expire(token, 30, TimeUnit.DAYS);
                    // 取到就返回true
                    return true;
                }

                // 设置响应状态为401 表示没有权限
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                return false;
            }

            // 不用过滤的url
        }).excludePathPatterns(patterns);
    }

}

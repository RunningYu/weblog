package com.weblog.utils;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * token工具类
 * @author : 其然乐衣Letitbe
 * @date : 2022/11/10
 */
@Component
public class TokenManager {

    /**
     * token有效时长
     */
    private long tokenExpiration = 24 * 60 * 60 * 1000;
    /**
     * 编码密钥
      */
    private String tokenSignKey = "123456";

    /**
     * 1. 使用 jwt 根据用户名生成token
      */
    public String createToken(String username) {
        String token = Jwts.builder()
                // 设置主体信息
                .setSubject(username)
                // 设置有效时长
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .signWith(SignatureAlgorithm.HS512, tokenSignKey).compressWith(CompressionCodecs.GZIP).compact();
        return token;
    }

    /**
     * 2. 根据token字符串得到用户信息
      */
    public String getUserInfoFromToken(String token) {
        String userinfo = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody().getSubject();
        return userinfo;
    }

    /**
     * 3. 删除token
     * 但是这个删除方法其实不需要我们写，因为token不需要咱们删，客户端不携带 token 就可以了
     */
    public void removeToken(String token) {}
}

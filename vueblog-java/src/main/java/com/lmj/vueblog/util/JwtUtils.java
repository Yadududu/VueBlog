package com.lmj.vueblog.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * jwt工具类
 * */
@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "lmjhub.jwt")
public class JwtUtils {

    private String secret;
    private long expire;
    private String header;

    /**
     * 生成jwt token
     * */
    public String generateToken(long userId) {
        Date nowDate = new Date();
        //过期时间
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")   //头部{"typ":"JWT"}
                .setSubject(userId+"")                //签发用户，{"sub":"userId"}
                .setIssuedAt(nowDate)                 //签发时间，{"iat" : "xxxxx"}
                .setExpiration(expireDate)            //设置过期时间{"exp" : "xxxx"}
                .signWith(SignatureAlgorithm.HS512, secret)     //编码方式HS512
                .compact();                           //把信息拼接起来
    }
    /**
     * 解析 token
     * */
    public Claims getClaimByToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            log.debug("validate is token error ", e);
            return null;
        }
    }

    /**
     * token是否过期
     * @return  true：过期
     * */
    public boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }
}

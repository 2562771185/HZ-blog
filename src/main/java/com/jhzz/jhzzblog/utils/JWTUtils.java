package com.jhzz.jhzzblog.utils;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/4/25
 * \* Time: 22:34
 * \* Description: JWT 用户登录功能
 * \
 */
public class JWTUtils {
    private static final String jwtToken = "345323Jzhz!@$*(";

    public static String createToken(Long userId){
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId",userId);
        JwtBuilder jwtBuilder = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,jwtToken) //签发算法 秘钥为jwtToken
                .setClaims(map)//body数据 唯一 自行设置
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+60*60*60*24*1000));//设置一天有效时间
        String token = jwtBuilder.compact();
        return token;
    }
    public static Map<String, Object> checkToken(String token){
        if (token==null){
            return null;
        }
        try {
            Jwt jwt = Jwts.parser().setSigningKey(jwtToken).parse(token);
            return (Map<String, Object>) jwt.getBody();
        } catch (ExpiredJwtException | IllegalArgumentException | MalformedJwtException | SignatureException e) {
            e.printStackTrace();
        }
        return null;
    }


}

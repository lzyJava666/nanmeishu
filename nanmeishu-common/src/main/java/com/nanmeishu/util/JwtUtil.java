package com.nanmeishu.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {


    /**
     * 生成jwt签名
     *
     * @param username 用户名
     * @param userId   用户ID
     * @return 加密的token
     */
    public static String sign(String username, String userId) {
        try {
            //过期时间为2小时
            Date date = new Date(System.currentTimeMillis() + 3600000);
            Algorithm algorithm = Algorithm.HMAC256("nanmeishuttt--1");
            Map<String, Object> header = new HashMap<>(2);
            header.put("typ", "JWT");
            header.put("alg", "HS256");
            return JWT.create()
                    .withHeader(header)
                    .withClaim("username", username)
                    .withClaim("userId", userId)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            return null;
        }


    }

    /**
     * 校验token是否正确
     *
     * @param token 密钥
     * @return 是否正确
     */
    public static boolean verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("nanmeishuttt--1");
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取token中携带的信息
     *
     * @param token 密钥
     * @param key   键
     * @return 值
     */
    public static String get(String token, String key) {
        try {
            DecodedJWT decode = JWT.decode(token);
            return decode.getClaim(key).asString();
        } catch (Exception e) {
            return null;
        }
    }

}

package com.nanmeishu.user.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * 全局常量
 */
@RefreshScope
@Component
public class AllConstant {
    //token过期时间
    private static int EXPIRE_DATE;
    //token密钥
    private static String TOKEN_SECRET;

    public static int getExpireDate() {
        return EXPIRE_DATE;
    }
    @Value("${TOKEN.DATE}")
    public  void setExpireDate(int expireDate) {
        EXPIRE_DATE = expireDate;
    }

    public static String getTokenSecret() {
        return TOKEN_SECRET;
    }
    @Value("${TOKEN.SECRET}")
    public void setTokenSecret(String tokenSecret) {
        TOKEN_SECRET = tokenSecret;
    }
}

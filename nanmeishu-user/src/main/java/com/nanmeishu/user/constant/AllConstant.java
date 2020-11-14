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
    //密码加密
    private static String PAS_MD5;

    public static String getPasMd5() {
        return PAS_MD5;
    }
    @Value("${PAS.MD5}")
    public  void setPasMd5(String pasMd5) {
        PAS_MD5 = pasMd5;
    }

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

package com.nanmeishu.enums;

/**
 * 错误码
 */
public enum ErrorCodeEnum {
    seccess(200),
    error(0);
    private int code;

   private ErrorCodeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

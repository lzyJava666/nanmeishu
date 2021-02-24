package com.nanmeishu.im.entity;

//消息码
public class MessageCode {
    //退出登录
    public static final int EXIT_LOGIN = 0;
    //登录
    public static final int LOGIN = 1;
    //强制下线
    public static final int COERCE_EXIT = 2;

    //系统推送消息
    public static final int SYSTEM_CHAR=888;

    //发送个人消息
    public static final int USER_CHAR = 11;

    //群聊
    public static final int GROUP_CHAR = 12;

    //收到个人消息
    public static final int TAKE_USER_CHAR = 13;

    //个人拉黑
    public static final int USER_BLACKLIST = 111;
    //个人屏蔽
    public static final int USER_ShIELDING = 112;
    //个人删除好友
    public static final int USER_DELETE = 113;
    //发起个人添加好友
    public static final int USER_ADD = 114;
    //回复个人添加好友
    public static final int RETURN_USER_ADD = 1141;
    //个人特别关心好友
    public static final int USER_MES_TOP = 116;

}

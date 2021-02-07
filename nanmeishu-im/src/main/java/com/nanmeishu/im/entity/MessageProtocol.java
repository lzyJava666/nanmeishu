package com.nanmeishu.im.entity;


import io.netty.util.CharsetUtil;
import lombok.Data;
import org.apache.tomcat.jni.Local;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;

/**
 * web-socket 消息类
 */
@Data
public class MessageProtocol {
    //长度
    private int len;
    //内容
    private String content;
    //发送消息的对象,0:退出登录 1:登录 2：私聊
    private int type;
    //所属用户
    private Long userId;
    //所属用户token
    private String token;
    //消息发送时间
    private LocalDateTime createTime;
    //消息是否已读
    private int isShow;
    //消息读取时间
    private LocalDateTime readTime;
    //接收消息用户id
    private Long fromId;
}


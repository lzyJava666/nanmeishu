package com.nanmeishu.im.entity;

import io.netty.channel.Channel;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 封装用户通道对象
 */
@Data
public class UserChannel {
    //通道ID
    private String channelId;
    //用户token
    private String token;
    //上线时间
    private LocalDateTime createTime;
    //离线时间
    private LocalDateTime endTime;
    //通道
    private Channel channel;


}

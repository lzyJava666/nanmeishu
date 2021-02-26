package com.nanmeishu.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;
@Data
@TableName("uu_friend")
@ApiModel("用户好友对象")
public class Friend implements Serializable {
    @ApiModelProperty("id")
    @TableId(type = IdType.ASSIGN_ID, value = "friend_id")
    private Long friendId;
    @ApiModelProperty("好友的user_id")
    private Long userId;
    @ApiModelProperty("好友对象")
    @TableField(exist = false)
    private User user;
    @ApiModelProperty("本人user_id")
    private Long myUserId;
    @ApiModelProperty("本人对象")
    @TableField(exist = false)
    private User myUser;
    @ApiModelProperty("添加时间")
    private LocalDateTime createTime;
    @ApiModelProperty("所属分组 -1 为 默认分组 默认值-1")
    private Integer groupId;
    @ApiModelProperty("状态 0：正常 1：消息不显示 2：拉黑 3：消息显示不通知 默认值 0")
    private Integer statuss;
    @ApiModelProperty("备注名")
    private String brName;
    @ApiModelProperty("特别关心 0 否 1是 默认值0")
    private Integer mesTop;
    @ApiModelProperty("我是否看他分享 0 是 1 否")
    private Integer isMyYour;
    @ApiModelProperty("他是否能看我分享 0 是 1 否")
    private Integer isYourMy;
}

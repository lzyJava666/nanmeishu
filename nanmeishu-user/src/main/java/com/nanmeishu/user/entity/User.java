package com.nanmeishu.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@TableName("u_user")
@ApiModel("用户对象")
public class User implements Serializable {
    @ApiModelProperty("用户id（修改时必需）")
    @TableId(type = IdType.ASSIGN_ID,value = "user_id")
    private Long userId;
    @ApiModelProperty("手机号（注册时必需）")
    private String phone;
    @ApiModelProperty("用户名（注册时必需）")
    private String username;
    @ApiModelProperty("密码（加密）（注册时必需）")
    private String password;
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty("身份证号")
    private String identityCard;
    @ApiModelProperty("年龄")
    private Integer age;
    @ApiModelProperty("性别： 0:未知 1：男 2：女")
    private Integer sex;
    @ApiModelProperty("详细地址")
    private String address;
    @ApiModelProperty("户籍所在地ID")
    private Long areaDetailsId;
    @ApiModelProperty("出生日期")
    private LocalDate dateOfBirth;
    @ApiModelProperty("是否已删除 0 ：否 1： 是")
    private Integer isDelete;
    @ApiModelProperty("最后登陆ip")
    private String ipNumber;
    @ApiModelProperty("角色ID")
    private Long roleId;
    @ApiModelProperty("头像url")
    private String headPortrait;

}

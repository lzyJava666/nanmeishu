package com.nanmeishu.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("uu_user")
@ApiModel("用户对象")
public class User implements Serializable {
    @ApiModelProperty("用户id（修改时必需）")
    @TableId(type = IdType.ASSIGN_ID, value = "user_id")
    private Long userId;
    @Length(min = 11, max = 11)
    @ApiModelProperty("手机号（注册时必需）")
    private String phone;
    @ApiModelProperty("用户名（注册时必需）")
    private String username;
    @ApiModelProperty("密码（加密）（注册时必需）")
    private String password;
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty("身份证号")
    @Pattern(regexp = "^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$",
            message = "必须为合法的大陆身份证号")
    private String identityCard;
    @ApiModelProperty("年龄")
    private Integer age;
    @ApiModelProperty("性别： 0:未知 1：男 2：女")
    @Range(max = 2, min = 0)
    private Integer sex;
    @ApiModelProperty("详细地址")
    private String address;
    @ApiModelProperty("户籍所在地ID")
    private Long areaDetailsId;
    @ApiModelProperty("出生日期")
    private LocalDate dateOfBirth;
    @ApiModelProperty("是否已删除 0 ：否 1： 是")
    @Range(max = 1, min = 0)
    private Integer isDelete;
    @ApiModelProperty("最后登陆ip")
    private String ipNumber;
    @ApiModelProperty("角色ID")
    private Long roleId;
    @ApiModelProperty("头像url")
    private String headPortrait;
    @ApiModelProperty("QQKey")
    private String qqKey;
    @ApiModelProperty("微信key")
    private String wxKey;
}

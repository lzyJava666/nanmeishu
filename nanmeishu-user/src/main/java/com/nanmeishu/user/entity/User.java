package com.nanmeishu.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@TableName("u_user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long userId;
    private String username;
    private String password;
    private LocalDateTime create_time;
    private String identity_card;
    private Integer age;
    private Integer sex;
    private String address;
    private Long area_details_id;
    private LocalDate date_of_birth;
    private Integer is_delete;
    private String ip_number;
    private Long role_id;

}

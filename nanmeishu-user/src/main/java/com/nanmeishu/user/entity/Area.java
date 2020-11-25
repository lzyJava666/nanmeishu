package com.nanmeishu.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
@TableName("u_area")
@Data
public class Area implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    private Long areaId;
    private String name;
    private Integer isDelete;
    private String br;
}

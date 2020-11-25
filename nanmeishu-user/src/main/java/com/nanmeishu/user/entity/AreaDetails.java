package com.nanmeishu.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("u_area_details")
public class AreaDetails implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    private Long areaDetailsId;
    private String name;
    private String br;
    private Integer isDelete;
}

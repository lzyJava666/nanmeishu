package com.nanmeishu.tale.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@TableName("t_statu")
@Data
@ApiModel("心情对象")
public class Statu {
    @ApiModelProperty("id")
    @TableId(type = IdType.ASSIGN_ID)
    private  Long statuId;
    @ApiModelProperty("心情名")
    private String name;
    @ApiModelProperty("心情描述")
    private String describe;
    @ApiModelProperty("url")
    private String statuUrl;
    @ApiModelProperty("是否启用 0 是 1 否")
    private Integer isDelete;
}

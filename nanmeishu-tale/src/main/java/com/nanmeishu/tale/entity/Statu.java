package com.nanmeishu.tale.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@TableName("tt_statu")
@Data
@ApiModel("心情对象")
public class Statu {
    @ApiModelProperty("id")
    @TableId(type = IdType.ASSIGN_ID)
    private Long statuId;
    @ApiModelProperty("心情名")
    private String name;
    @ApiModelProperty("心情描述")
    private String statuDescribe;
    @ApiModelProperty("url")
    private String statuUrl;
    @Range(max = 1, min = 0)
    @ApiModelProperty("是否启用 0 是 1 否")
    private Integer isDelete;
    @Range(max = 99, min = 0)
    @ApiModelProperty("排序 数值越大排前面")
    private Integer sort;
}

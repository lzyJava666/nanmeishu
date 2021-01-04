package com.nanmeishu.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
@ApiModel("地区详情对象")
@Data
@TableName("uu_area_details")
public class AreaDetails implements Serializable {
    @ApiModelProperty("地区详情id")
    @TableId(type = IdType.ASSIGN_ID)
    private Long areaDetailsId;
    @ApiModelProperty("地区详情名")
    private String name;
    @ApiModelProperty("备注")
    private String br;
    @Range(max = 1,min = 0)
    @ApiModelProperty("是否删除 0 否 1是")
    private Integer isDelete;
    @ApiModelProperty("地区ID")
    private Long areaId;
}

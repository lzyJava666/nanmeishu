package com.nanmeishu.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.util.List;

@TableName("uu_area")
@Data
@ApiModel("地区对象")
public class Area implements Serializable {
    @ApiModelProperty("地区ID")
    @TableId(type = IdType.ASSIGN_ID)
    private Long areaId;
    @ApiModelProperty("地区名")
    private String name;
    @ApiModelProperty("是否删除 0 否 1是")
    @Range(max = 1, min = 0)
    private Integer isDelete;
    @ApiModelProperty("备注")
    private String br;
    @ApiModelProperty("地区详情列表对象")
    @TableField(exist = false)
    List<AreaDetails> areaDetails;
}

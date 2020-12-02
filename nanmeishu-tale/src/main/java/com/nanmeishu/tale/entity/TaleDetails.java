package com.nanmeishu.tale.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("故事明细对象")
@TableName("t_tale_details")
@Data
public class TaleDetails implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty("故事明细ID")
    private Long taleDetailsId;
    @ApiModelProperty("内容")
    private String content;
    @ApiModelProperty("标题---不超过15个字")
    private String taleTitle;
    @ApiModelProperty("简单描述---不超过50个字")
    private String titleHead;
    @ApiModelProperty("故事id")
    private Long taleId;
}

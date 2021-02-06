package com.nanmeishu.tale.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@TableName("tt_tale")
@ApiModel("故事对象")
@Data
public class Tale {
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty("故事ID (修改时不能为空)")
    private Long taleId;
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime createTime;
    @ApiModelProperty("修改时间")
    private LocalDateTime updateTime;
    @ApiModelProperty("故事开始日期 (新增时不能为空)")
    private LocalDate frontDate;
    @ApiModelProperty("故事结束日期 (新增时不能为空)")
    private LocalDate endDate;
    @Range(min = 0, max = 1)
    @ApiModelProperty("是否删除 0:否 1：是")
    private Integer isDelete;
    @Range(max = 4, min = 0)
    @ApiModelProperty("类型 0：日记 1：经历 2：亿人 3 ：亿事 4 ：其他 (新增时不能为空)")
    private Integer type;
    @ApiModelProperty("对象（与类型对应，类型->对象对应关系如下{0:心情id，1-4：自定义}）")
    private String objectt;
    @ApiModelProperty("所属用户id (新增时不能为空)")
    private Long userId;
    @TableField(exist = false)
    @ApiModelProperty("故事明细对象")
    private TaleDetails taleDetails;

}

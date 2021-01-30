package com.nanmeishu.transaction.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.tomcat.jni.Local;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@ApiModel("代办事务类")
@TableName("s_transaction")
public class Transaction implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty("事务ID类")
    private String transactionId;
    @ApiModelProperty("事务内容")
    private String content;
    @ApiModelProperty("事务创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty("时长 单位秒，-1未永久性任务，默认值：-1")
    private Integer duration;
    @ApiModelProperty("状态 0 ：未完成 1：已完成 2：暂停 默认值 0")
    private Integer statuss;
    @ApiModelProperty("延长时间 单位秒")
    private Integer extendDate;
    @ApiModelProperty("所属用户id")
    private Long userId;
    @ApiModelProperty("开始日期 2000-01-01 为每日必做")
    private LocalDate startDate;
    @ApiModelProperty("事务明细列表对象")
    @TableField(exist = false)
    List<TransactionDetail> transactionDetails;
    @ApiModelProperty("开始时间 00.00.44 为不规定时间")
    private LocalTime startTime;
    @ApiModelProperty("完成时间")
    private LocalDateTime commitDateTime;
    @ApiModelProperty("是否置顶 0 不置顶 1-9置顶，越大排越前")
    private Integer tops;
}

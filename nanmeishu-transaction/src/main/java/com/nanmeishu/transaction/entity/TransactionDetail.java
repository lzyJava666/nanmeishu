package com.nanmeishu.transaction.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel("事务明细")
@TableName("s_transaction_detail")
public class TransactionDetail implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty("代办事务明细ID")
    private Long transactionDetailId;
    @ApiModelProperty("代办事务明细内容")
    private String content;
    @ApiModelProperty("事务明细完成时间")
    private LocalDateTime commitTime;
    @ApiModelProperty("0:未完成 1：已完成 2：暂停 3：已过时 ")
    private Integer statuss;
    private Long transactionId;
}

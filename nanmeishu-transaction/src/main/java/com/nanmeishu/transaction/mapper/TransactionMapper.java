package com.nanmeishu.transaction.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nanmeishu.transaction.entity.Transaction;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TransactionMapper extends BaseMapper<Transaction> {
    /**
     * 查看用户当天事务，通过用户id
     * @param userId
     * @param type
     * @param startDate
     * @return
     */
    List<Transaction> getById(@Param("userId") String userId,@Param("type") int type,@Param("startDate") String startDate);
    /**
     * 查看用户当天事务，通过用户id(不区分状态)
     * @param userId
     * @param startDate
     * @return
     */
    List<Transaction> getByIdNoType(@Param("userId") String userId,@Param("startDate") String startDate);
}
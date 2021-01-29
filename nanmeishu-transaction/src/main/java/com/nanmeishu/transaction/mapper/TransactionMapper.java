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
     * 获取一个时间段的事务，通过用户id
     * @param userId
     * @param type
     * @param weekDate1
     * @param weekDate2
     * @return
     */
    List<Transaction> getByIdAndWeek(@Param("userId") String userId,
                                     @Param("type") int type,
                                     @Param("weekDate1") String weekDate1,
                                     @Param("weekDate2") String weekDate2);

    /**
     * 获取当前用户全部事务
     * @param userId
     * @return
     */
    List<Transaction> getByIdAll(@Param("userId") String userId);
}

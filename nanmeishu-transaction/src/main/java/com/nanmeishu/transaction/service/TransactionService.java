package com.nanmeishu.transaction.service;

import com.nanmeishu.transaction.entity.Transaction;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
    /**
     * 通过用户id获取用户所有事务及代办事务明细
     *
     * @param userId
     * @return
     */
    List<Transaction> getById(String userId, int type, LocalDate startDate, int status);

    /**
     * 修改事务内容，通过id
     *
     * @param transaction
     */
    void update(Transaction transaction);

    /**
     * 删除指定代办事务
     *
     * @param transactionId
     */
    void delete(long transactionId);

    /**
     * 添加新事务---不包含事务明细
     *
     * @param transaction
     */
    void save(Transaction transaction);

    /**
     * 统计当前用户事务数量
     * @param userId
     * @return
     */
    Integer countTransactionByUserId(String userId);
}

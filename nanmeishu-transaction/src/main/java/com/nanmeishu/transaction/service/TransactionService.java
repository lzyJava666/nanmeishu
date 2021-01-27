package com.nanmeishu.transaction.service;

import com.nanmeishu.transaction.entity.Transaction;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
    /**
     * 通过用户id获取用户所有事务及代办事务明细
     * @param userId
     * @return
     */
    List<Transaction> getById(String userId, int type, LocalDate startDate);
}

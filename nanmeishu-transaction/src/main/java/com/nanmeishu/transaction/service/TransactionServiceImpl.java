package com.nanmeishu.transaction.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nanmeishu.transaction.entity.Transaction;
import com.nanmeishu.transaction.mapper.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service("transactionService")
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionMapper transactionMapper;

    @Override
    public List<Transaction> getById(String userId, int type, LocalDate startDate) {
        List<Transaction> transactions = transactionMapper.getById(userId,type,startDate);
        return transactions;
    }
}

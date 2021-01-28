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
        List<Transaction> transactions=null;
        if(type==-1){
            //不区分状态
            transactions = transactionMapper.getByIdNoType(userId,startDate.toString());
        }else{
            transactions = transactionMapper.getById(userId,type,startDate.toString());
        }

        return transactions;
    }

    @Override
    public void update(Transaction transaction) {
        int i = transactionMapper.updateById(transaction);
        if(i<=0){
            throw new RuntimeException("修改事务内容失败!请重试");
        }
    }
}

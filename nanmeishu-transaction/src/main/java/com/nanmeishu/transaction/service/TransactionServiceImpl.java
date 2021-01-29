package com.nanmeishu.transaction.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nanmeishu.transaction.entity.Transaction;
import com.nanmeishu.transaction.entity.TransactionDetail;
import com.nanmeishu.transaction.mapper.TransactionDetailMapper;
import com.nanmeishu.transaction.mapper.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service("transactionService")
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionMapper transactionMapper;

    @Autowired
    TransactionDetailMapper transactionDetailMapper;

    @Override
    public List<Transaction> getById(String userId, int type, LocalDate startDate,int status) {
        List<Transaction> transactions=null;
            switch (status){
                case 1:{
                    //类型一 明天事务
                    transactions = transactionMapper.getById(userId,type,startDate.toString());
                }
                break;
                case 2:{
                    //类型二：明天事务
                    startDate=startDate.plusDays(1);
                    transactions = transactionMapper.getById(userId,type,startDate.toString());
                }
                break;
                case 3:{
                    //类型三：本周事务
                    //获取当前日期startDate的周一
                    LocalDate weekDate1=startDate.with(TemporalAdjusters.previousOrSame( DayOfWeek.MONDAY));
                    //获取当前日期startDate的周日
                    LocalDate weekDate2=startDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
                    transactions = transactionMapper.getByIdAndWeek(userId,type,weekDate1.toString(),weekDate2.toString());
                }
                break;
                case 4:{
                    //类型四：本月事务
                    //这个月的第一天
                    LocalDate monthStart = startDate.with(TemporalAdjusters.firstDayOfMonth());
                    //这个月的最后一天
                    LocalDate monthEnd = startDate.with(TemporalAdjusters.lastDayOfMonth());
                    transactions = transactionMapper.getByIdAndWeek(userId,type,monthStart.toString(),monthEnd.toString());
                }
                case 5:{
                    //类型五：全部事务
                    transactions = transactionMapper.getByIdAll(userId);
                }
                break;
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

    @Transactional
    @Override
    public void delete(long transactionId) {
        transactionDetailMapper.delete(new QueryWrapper<TransactionDetail>().eq("transaction_id",transactionId));
        int i = transactionMapper.deleteById(transactionId);
        if(i<=0){
            throw new RuntimeException("删除失败");
        }
    }
}

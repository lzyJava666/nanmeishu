package com.nanmeishu.tale.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nanmeishu.tale.entity.Statu;
import com.nanmeishu.tale.mapper.StatuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("statuService")
public class StatuServiceImpl implements StatuService {
    @Autowired
    private StatuMapper statuMapper;

    @Override
    public List<Statu> listPageStatu() {
        return statuMapper.selectList(new QueryWrapper<Statu>().orderByDesc("sort"));
    }
}

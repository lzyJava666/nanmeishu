package com.nanmeishu.tale.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nanmeishu.tale.entity.Tale;
import com.nanmeishu.tale.entity.TaleDetails;
import com.nanmeishu.tale.mapper.TaleDetailsMapper;
import com.nanmeishu.tale.mapper.TaleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("taleService")
public class TaleServiceImpl implements TaleService {

    @Autowired
    TaleMapper taleMapper;

    @Autowired
    TaleDetailsMapper taleDetailsMapper;

    @Transactional
    @Override
    public void saveTaleAndDetails(Tale tale) {
        int i = taleMapper.insert(tale);
        if(i<=0){
            throw new RuntimeException("创建时出错了！");
        }
        TaleDetails taleDetails = tale.getTaleDetails();
        taleDetails.setTaleId(tale.getTaleId());
        int insert = taleDetailsMapper.insert(taleDetails);
        if(insert<=0){
            throw new RuntimeException("创建时出错了！");
        }
    }

    @Override
    public List<Tale> listByUserId(String userId) {
        List<Tale> taleList = taleMapper.selectList(new QueryWrapper<Tale>().eq("user_id", userId));
        for (Tale tale : taleList) {
            tale.setTaleDetails(taleDetailsMapper.selectOne(new QueryWrapper<TaleDetails>().eq("tale_id",tale.getTaleId())));
        }
        return taleList;
    }
}

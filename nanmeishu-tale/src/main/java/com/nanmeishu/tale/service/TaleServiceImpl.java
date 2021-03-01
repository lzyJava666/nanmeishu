package com.nanmeishu.tale.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nanmeishu.tale.entity.Tale;
import com.nanmeishu.tale.entity.TaleDetails;
import com.nanmeishu.tale.mapper.TaleDetailsMapper;
import com.nanmeishu.tale.mapper.TaleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        if (i <= 0) {
            throw new RuntimeException("创建时出错了！");
        }
        TaleDetails taleDetails = tale.getTaleDetails();
        taleDetails.setTaleId(tale.getTaleId());
        int insert = taleDetailsMapper.insert(taleDetails);
        if (insert <= 0) {
            throw new RuntimeException("创建时出错了！");
        }
    }

    @Override
    public Map<String, Object> listByUserId(String userId, String pageNum, String pageSize) {
        Map<String, Object> map = new HashMap<>();
        IPage<Tale> page = new Page<>(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
        IPage<Tale> taleIPage = taleMapper.selectPage(page, new QueryWrapper<Tale>()
                .eq("user_id", userId)
                .orderByDesc("create_time"));
        List<Tale> taleList = taleIPage
                .getRecords();
        for (Tale tale : taleList) {
            tale.setTaleDetails(taleDetailsMapper.selectOne(new QueryWrapper<TaleDetails>().eq("tale_id", tale.getTaleId())));
        }
        map.put("tales", taleList);
        map.put("total", taleIPage.getTotal());
        return map;
    }

    @Transactional
    @Override
    public void updateTaleAndDetails(Tale tale) {
        int i = taleMapper.updateById(tale);
        if (i <= 0) {
            throw new RuntimeException("更新出错");
        }
        TaleDetails taleDetails = tale.getTaleDetails();
        if (taleDetails == null || taleDetails.getTaleDetailsId() == null) {
            return;
        }
        int i1 = taleDetailsMapper.updateById(taleDetails);
        if (i1 <= 0) {
            throw new RuntimeException("更新出错");
        }
    }

    @Override
    public Integer countTaleByUserId(String userId) {
        return taleMapper.selectCount(new QueryWrapper<Tale>().eq("type",0).eq("user_id",userId));
    }
}

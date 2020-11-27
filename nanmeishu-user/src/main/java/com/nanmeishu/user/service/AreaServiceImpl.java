package com.nanmeishu.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nanmeishu.user.entity.Area;
import com.nanmeishu.user.entity.AreaDetails;
import com.nanmeishu.user.mapper.AreaDetailsMapper;
import com.nanmeishu.user.mapper.AreaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("areaService")
public class AreaServiceImpl implements AreaService{
    @Autowired
    AreaMapper areaMapper;

    @Autowired
    AreaDetailsMapper areaDetailsMapper;

    @Override
    public List<Area> listAreaAndDetails() {
        List<Area> areas = areaMapper.selectList(new QueryWrapper<>());
        for (Area area : areas) {
            List<AreaDetails> areaDetailsList = areaDetailsMapper.selectList(new QueryWrapper<AreaDetails>().eq("area_id", area.getAreaId()));
            area.setAreaDetails(areaDetailsList);
        }
        return areas;
    }

    @Override
    public List<AreaDetails> listDetailsByAreaId(String areaId) {
        return areaDetailsMapper.selectList(new QueryWrapper<AreaDetails>().eq("area_id",areaId));
    }


}

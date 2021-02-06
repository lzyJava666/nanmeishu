package com.nanmeishu.user.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nanmeishu.user.entity.Area;
import com.nanmeishu.user.entity.AreaDetails;
import com.nanmeishu.user.mapper.AreaDetailsMapper;
import com.nanmeishu.user.mapper.AreaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("areaService")
public class AreaServiceImpl implements AreaService {
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
        return areaDetailsMapper.selectList(new QueryWrapper<AreaDetails>().eq("area_id", areaId));
    }

    @Transactional
    @Override
    public void listSave(List<Map> lists) {
        Area area;
        AreaDetails areaDetails;
        for (Map map : lists) {
            area = new Area();
            area.setName(map.get("cityName").toString());
            int insert = areaMapper.insert(area);
            if (insert <= 0) {
                throw new RuntimeException("插入失败");
            }
            List<Map> a1 = JSON.parseArray(map.get("nodes").toString(), Map.class);
            List<Map> enMap = JSON.parseArray(a1.get(0).get("nodes").toString(), Map.class);
            //List<Map> enMap= JSON.parseArray(JSON.parseObject(map.get("nodes").toString(),List.class).get(0).toString(),Map.class);
            for (Map mao : enMap) {
                areaDetails = new AreaDetails();
                areaDetails.setAreaId(area.getAreaId());
                areaDetails.setName(mao.get("cityName").toString());
                int insert1 = areaDetailsMapper.insert(areaDetails);
                if (insert1 <= 0) {
                    throw new RuntimeException("插入失败");
                }
            }
        }
    }


}

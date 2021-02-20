package com.nanmeishu.user.service;

import com.nanmeishu.user.entity.Area;
import com.nanmeishu.user.entity.AreaDetails;
import java.util.List;
import java.util.Map;

public interface AreaService {
    /**
     * 拿到地区列表及其详情
     *
     * @return
     */
    List<Area> listAreaAndDetails();

    /**
     * 通过地区ID拿到详情列表
     *
     * @param areaId 地区id
     * @return 详情列表
     */
    List<AreaDetails> listDetailsByAreaId(String areaId);

    /**
     * 外部导入地区
     *
     * @param lists
     */
    void listSave(List<Map> lists);
}

package com.nanmeishu.tale.service;

import com.nanmeishu.tale.entity.Tale;

import java.util.List;
import java.util.Map;

public interface TaleService {
    /**
     * 新增故事及其详情
     *
     * @param tale
     */
    void saveTaleAndDetails(Tale tale);

    /**
     * 获取用户故事列表
     *
     * @param userId
     * @return
     */
    Map<String, Object> listByUserId(String userId, String pageNum, String pageSize);

    /**
     * 更新故事及其明细
     *
     * @param tale
     */
    void updateTaleAndDetails(Tale tale);

    /**
     * 统计用户故事数量
     * @param userId
     * @return
     */
    Integer countTaleByUserId(String userId);

    /**
     * 删除指定日记
     * @param userId
     * @param taleId
     */
    void deleteTale(String userId, String taleId);

    /**
     * 通过taleid获取详细信息
     * @param taleId
     * @return
     */
    Tale getTale(String taleId);
}

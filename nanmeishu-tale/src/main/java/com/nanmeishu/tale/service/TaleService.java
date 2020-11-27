package com.nanmeishu.tale.service;

import com.nanmeishu.tale.entity.Tale;

import java.util.List;

public interface TaleService {
    /**
     * 新增故事及其详情
     * @param tale
     */
    void saveTaleAndDetails(Tale tale);

    /**
     * 获取用户故事列表
     * @param userId
     * @return
     */
    List<Tale> listByUserId(String userId);
}

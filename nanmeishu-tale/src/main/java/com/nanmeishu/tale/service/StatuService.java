package com.nanmeishu.tale.service;

import com.nanmeishu.tale.entity.Statu;

import java.util.List;

public interface StatuService {
    /**
     * 模糊查询所有表情，最多九个
     *
     * @return
     */
    List<Statu> listPageStatu();
}
